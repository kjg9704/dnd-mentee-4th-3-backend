package dnd.jackpot.user;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dnd.jackpot.notification.InterestSubscribe;
import dnd.jackpot.notification.PushService;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectParticipantRequestDto;
import dnd.jackpot.project.dto.ProjectParticipantRequestDto.RequestResponse;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.Estack;
import dnd.jackpot.project.entity.ProjectParticipantRequest;
import dnd.jackpot.project.entity.ProjectScrap;
import dnd.jackpot.project.repository.ProjectParticipantRepository;
import dnd.jackpot.project.repository.ProjectParticipantRequestRepository;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.project.repository.ProjectScrapRepository;
import dnd.jackpot.project.service.CommentService;
import dnd.jackpot.project.service.ProjectService;
import dnd.jackpot.response.BasicResponse;
import dnd.jackpot.response.CommonResponse;
import dnd.jackpot.response.ErrorResponse;
import dnd.jackpot.response.Response;
import dnd.jackpot.security.JwtRequest;
import dnd.jackpot.security.JwtResponse;
import dnd.jackpot.security.JwtTokenUtil;
import dnd.jackpot.security.JwtUserDetailsService;
import dnd.jackpot.user.UserDto.otherResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(description = "로그인/회원가입 컨트롤러")
@RestController
@RequiredArgsConstructor
public class UserController {

	private final JwtUserDetailsService userService;
	private final ProjectScrapRepository projectScrapRepo;
	private final ProjectService projectService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private final UserRepository userRepo;
	private final CommentService commentService;
	private final ProjectParticipantRequestRepository projectParticipantRequestRepo;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@ApiOperation(value = "로그인")
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@ApiParam(value = "이메일, 패스워드, 로그인타입")@RequestBody JwtRequest authenticationRequest) throws Exception {
		String token;
		try {
			authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
			final dnd.jackpot.user.User userDetails = (dnd.jackpot.user.User) userService.loadUserByEmailAndLogintype(authenticationRequest.getEmail(), authenticationRequest.getLogintype());		
			token = jwtTokenUtil.generateToken(userDetails);
			if(!authenticationRequest.getRegistrationToken().equals(userDetails.getRegistrationToken())) {
				userService.modifyRegistrationToken(authenticationRequest.getRegistrationToken(), userDetails);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 유저가 없습니다. 회원가입이 필요합니다."));
		}
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@ApiOperation(value = "카카오 로그인 검증")
	@PostMapping(value = "/kakaoLogin")
	public ResponseEntity<?> kakaoLoginRequest(@ApiParam(value = "JSON 형식으로 ")@RequestBody SnsRequestDto request){
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + request.getSnsToken());
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		HttpEntity<MultiValueMap<String, String>> kakaoRequest =
				new HttpEntity<>(headers);
		ResponseEntity<String> response = rt.exchange(
				"https://kapi.kakao.com/v1/user/access_token_info",
				HttpMethod.GET,
				kakaoRequest,
				String.class
				);
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.getBody());
		System.out.println(response.getBody());
		String id = jsonObject.get("id").toString();
		
		String token = "";
		try {
			dnd.jackpot.user.User userDetails = (dnd.jackpot.user.User) userService.loadUserByEmailAndLogintype(id, "kakao");
			token = jwtTokenUtil.generateToken(userDetails);
			if(!request.getRegistrationToken().equals(userDetails.getRegistrationToken())) {
				userService.modifyRegistrationToken(request.getRegistrationToken(), userDetails);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 유저가 없습니다. 회원가입이 필요합니다.", "404"));
		}
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@ApiOperation(value = "네이버 로그인 검증")
	@PostMapping(value = "/naverLogin")
	public ResponseEntity<?> naverLoginRequest(@ApiParam(value = "JSON 형식") @RequestBody SnsRequestDto request){
		String header = "Bearer " + request.getSnsToken(); // Bearer 다음에 공백 추가
		System.out.println("------ 헤더확인");
		System.out.println(header);
		StringBuffer response = new StringBuffer();
		try {
			String apiURL = "https://openapi.naver.com/v1/nid/me";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", header);
			int responseCode = con.getResponseCode();
			System.out.println(responseCode + "-------");
			BufferedReader br;
			if(responseCode==200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());
		} catch (Exception e) {
			
		}
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response.toString());
		JsonObject res = (JsonObject) jsonObject.get("response");
		if(res == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("토큰 인증 실패", "500"));
		}
		String id = res.get("id").toString();
		id = id.substring(1, id.length()-1);
		String token = "";
		try {
			dnd.jackpot.user.User userDetails = (dnd.jackpot.user.User) userService.loadUserByEmailAndLogintype(id, "naver");
			token = jwtTokenUtil.generateToken(userDetails);
			if(!request.getRegistrationToken().equals(userDetails.getRegistrationToken())) {
				userService.modifyRegistrationToken(request.getRegistrationToken(), userDetails);
			}
			System.out.println(token);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 유저가 없습니다. 회원가입이 필요합니다.", "404"));
		}
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@ApiOperation(value = "구글 로그인 검증")
	@PostMapping(value = "/googleLogin")
	public ResponseEntity<?> googleLoginRequest(@ApiParam(value = "JSON 형식") @RequestBody SnsRequestDto request){
		RestTemplate rt = new RestTemplate();
		System.out.println("Authorization"+ "Bearer " + request.getSnsToken());
		String response = "";
		try {
			response = rt.getForEntity("https://oauth2.googleapis.com/tokeninfo?id_token=" + request.getSnsToken(), String.class).getBody().toString();
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("토큰 인증 실패", "500"));
		}
		System.out.println(response);
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(response);
		String id = jsonObject.get("sub").toString();
		System.out.println(id);
		id = id.substring(1, id.length()-1);
		String token = "";
		try {
			dnd.jackpot.user.User userDetails = (dnd.jackpot.user.User) userService.loadUserByEmailAndLogintype(id, "google");
			token = jwtTokenUtil.generateToken(userDetails);
			if(!request.getRegistrationToken().equals(userDetails.getRegistrationToken())) {
				userService.modifyRegistrationToken(request.getRegistrationToken(), userDetails);
			}
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 유저가 없습니다. 회원가입이 필요합니다.", "404"));
		}
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	@ApiOperation(value = "회원탈퇴 (parameter 없이 header에 토큰값만 있으면됨)")
	@DeleteMapping("/withdraw")
	public ResponseEntity<? extends BasicResponse> withDraw(@AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			String userEmail = user.getUsername();
			String loginType = userService.getLoginType(user);
			userService.deleteUser(userEmail, loginType);
			userService.deletedSave(userEmail, loginType);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("탈퇴 처리 실패"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}

	@ApiOperation(value = "회원가입")
	@PostMapping("/signup")
	@Transactional
	public ResponseEntity<? extends BasicResponse> signup(@RequestBody @ApiParam(value = "가입 유저 정보 \n auth = ROLE_USER, logintype = normal or socialLogintype(google, kakao, naver)") UserDto infoDto) {
		try {
			userService.save(infoDto);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("회원가입 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "프로필 수정")
	@PutMapping("/update-profile")
	public ResponseEntity<? extends BasicResponse> updateProfile(@RequestBody @ApiParam(value = "") UserModifyDto infoDto, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			userService.modifyUser(infoDto, user);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("업데이트 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "비밀번호 모를때 이메일 인증 후 변경")
	@PutMapping("/password-modify")
	public ResponseEntity<? extends BasicResponse> passwordModify(@RequestParam(value="email") @ApiParam(value = "유저 이메일") String email, @RequestParam(value="password") @ApiParam(value = "새로운 비밀번호") String password) {
		try {
			userService.modifyPassword(email, password);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("failed", "404"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "유저 비밀번호 변경(비밀번호만 넘겨주면됨) 토큰은필수입니다")
	@PutMapping("/password-change")
	public ResponseEntity<? extends BasicResponse> normalPasswordModify(@RequestParam(value="password") @ApiParam(value = "기존 비밀번호") String existingPassword, 
																		@RequestParam(value="newpassword") @ApiParam(value = "새 비밀번호") String newPassword,
																		@AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String dbPassword = user.getPassword();
			if (passwordEncoder.matches(existingPassword, dbPassword)) {
				userService.modifyPassword(newPassword, user);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ErrorResponse("failed", "404"));
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("failed", "404"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	
	@ApiOperation(value = "이메일 중복확인")
	@GetMapping("/email/is-exist")
	public ResponseEntity<?> isExistEmail(@RequestParam("email") @ApiParam(value = "이메일") String email){
		Response response = new Response();
		if(userService.isExistEmail(email)) {
			response.setMessage("이미 가입한 회원입니다");
			return ResponseEntity.ok(response); 
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("사용가능", "404"));
		}
	}
	@ApiOperation(value = "닉네임 중복확인")
	@GetMapping("/name/is-exist")
	public ResponseEntity<?> isExistName(@RequestParam("name") @ApiParam(value = "닉네임") String name){
		Response response = new Response();
		if(userService.isExistName(name)) {
			response.setMessage("이미 존재하는 닉네임 입니다");
			return ResponseEntity.ok(response); 
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("사용가능", "404"));
		}
	}
	
	@ApiOperation(value = "프로필 정보 가져오기")
	@GetMapping("/myprofile")
	@Transactional
	public ResponseEntity<? extends BasicResponse> getMyProfile(@AuthenticationPrincipal dnd.jackpot.user.User user){
		List<UserStacks> values;
		List<Estack> stacks;
		List<Einterest> interests = new ArrayList<>();
		List<InterestSubscribe> interValues;
		List<ProjectDto> scrapList = new ArrayList<>();
		UserDto.profileResponse userDto;
		try {
			stacks = new ArrayList<Estack>();
			dnd.jackpot.user.User persistenceUser = userRepo.getOne(user.getUserIndex());
			values = persistenceUser.getStacks();
			List<ProjectDto> projects = projectService.findAllByAuthor(persistenceUser);
			List<ProjectDto> participantList = projectService.findAllByParticipant(persistenceUser);
			List<ProjectParticipantRequest> requestList = projectParticipantRequestRepo.findAllByUser(persistenceUser);
			List<ProjectDto> commentList = commentService.getAllProjectsByUser(persistenceUser);
			List<ProjectScrap> objlist = projectScrapRepo.findAllByUser(persistenceUser);
			List<ProjectDto> requestResult = new ArrayList<>();
			for(ProjectScrap list : objlist) {
				scrapList.add(projectService.findById(list.getProject().getId()));
			}
			interValues = persistenceUser.getSubscribes();
			for(UserStacks st : values) {
				stacks.add(st.getStack());
			}
			for(InterestSubscribe is : interValues) {
				interests.add(is.getInterest());
			}
			
			for(ProjectParticipantRequest list : requestList) {
				requestResult.add(projectService.findById(list.getProject().getId()));
			}
			userDto = new UserDto.profileResponse(user.getUserIndex(), user.getName(), user.getRegion(), user.getPosition(), stacks, user.isPrivacy(), user.getLoginType(), user.getCareer(), user.getAuth(), user.getEmoticon(), user.getIntroduction(), user.getPortfolioLink1(), user.getPortfolioLink2(), user.isCommentPush(), user.isRequestPush(), user.isRequestAcceptPush(), interests, projects, participantList , requestResult, commentList, scrapList);
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("failed", "404"));
		}
		return ResponseEntity.ok().body(new CommonResponse<UserDto.profileResponse>(userDto));
	}
	
	@ApiOperation(value = "프로필 정보 가져오기")
	@GetMapping("/user/{id}")
	public ResponseEntity<? extends BasicResponse> getOne(@PathVariable("id") Long id) {
		List<UserStacks> values;
		List<Estack> stacks;
		UserDto.otherResponse userDto;
		try {
			dnd.jackpot.user.User user = userService.loadUserByUserIndex(id);
			values = user.getStacks();
			stacks = new ArrayList<Estack>();
			for(UserStacks st : values) {
				stacks.add(st.getStack());
			}
			userDto = new UserDto.otherResponse(user.getUserIndex(), user.getName(), user.getRegion(), user.getPosition(), stacks, user.isPrivacy(), user.getCareer(), user.getAuth(), user.getEmoticon(), user.getIntroduction(), user.getPortfolioLink1(), user.getPortfolioLink2());
		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("failed", "404"));
		}
		return ResponseEntity.ok().body(new CommonResponse<UserDto.otherResponse>(userDto));
	}
	
    
    @ApiOperation(value = "유저 스크랩 게시글 목록 조회")
	@GetMapping("/myprojectscrap")
	public ResponseEntity<?> getMyProjectScrap(@ApiParam(value = "토큰만 넘기면됨 parameter 없음 ") @AuthenticationPrincipal dnd.jackpot.user.User user) {
		List<ProjectDto> projectList = new ArrayList<>();
		try {
			List<ProjectScrap> objlist = projectScrapRepo.findAllByUser(user);
			for(ProjectScrap list : objlist) {
				projectList.add(projectService.findById(list.getProject().getId()));
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("스크랩 실패", "500"));
		}
			return ResponseEntity.ok().body(new CommonResponse<ProjectDto>(projectList));
	}
    
    @ApiOperation(value = "유저 스크랩 처리")
	@PostMapping("/userscrap/{id}")
	public ResponseEntity<?> addUserScrap(@ApiParam(value = "스크랩대상 userIndex, 헤더에 토큰 ") @PathVariable("id") long userIndex, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			userService.addUserScrap(userIndex, user);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("스크랩 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
    
    @ApiOperation(value = "유저 스크랩 취소")
	@DeleteMapping("/deleteuserscrap/{id}")
	public ResponseEntity<?> deleteUserScrap(@ApiParam(value = "취소 대상 userIndex, 헤더에 토큰 ") @PathVariable("id") long userIndex, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			userService.deleteUserScrap(userIndex, user);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("failed", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
    
    @ApiOperation(value = "스크랩한 유저 목록 조회")
	@GetMapping("/myuserscrap")
	public ResponseEntity<?> getMyUserScrap(@ApiParam(value = "토큰만 넘기면됨 parameter 없음 ") @AuthenticationPrincipal dnd.jackpot.user.User user) {
    	List<otherResponse> list;
		try {
			list = userService.getScrappingUsers(user);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("스크랩 실패", "500"));
		}
			return ResponseEntity.ok().body(new CommonResponse<otherResponse>(list));
	}
    
    @ApiOperation(value = "알림설정 수정")
	@PutMapping("/update-push")
    public ResponseEntity<?> setPush(@RequestBody PushSetDto pushSet, @AuthenticationPrincipal dnd.jackpot.user.User user){
    	try {
    		userService.setPush(pushSet, user);
    	}catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("failed", "500"));
    	}
    	return ResponseEntity.ok().body(new Response("success"));
    }
	
	
}