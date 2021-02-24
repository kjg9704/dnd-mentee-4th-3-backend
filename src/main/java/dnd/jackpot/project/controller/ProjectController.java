package dnd.jackpot.project.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessagingException;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dnd.jackpot.project.service.CommentService;
import dnd.jackpot.project.service.ProjectService;
import dnd.jackpot.response.BasicResponse;
import dnd.jackpot.response.CommonResponse;
import dnd.jackpot.response.ErrorResponse;
import dnd.jackpot.response.Response;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.notification.PushService;
import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.dto.RequestAcceptDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectScrap;
import dnd.jackpot.project.repository.ProjectParticipantRequestRepository;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.project.repository.ProjectScrapRepository;

@Api(description = "프로젝트 게시글 관련 api")
@RestController
@RequiredArgsConstructor
public class ProjectController {
	private final ProjectService service;
	private final CommentService commentService;
	private final ProjectScrapRepository projectScrapRepo;
	private final ProjectRepository projectRepo;
	private final PushService pushService;
	private final ProjectParticipantRequestRepository projectParticipantRequestRepo;
	private final UserRepository userRepo;

	@ApiOperation(value = "게시글 작성")
	@PostMapping("/api/projects")
	public ResponseEntity<? extends BasicResponse> save(@ApiParam(value = "RequestBody에 json형식") @RequestBody ProjectSaveDto saveDto, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			service.save(saveDto, user);
//			pushService.sendInterestSubscribe(saveDto.getInterest());
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("게시글 작성 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}

	@ApiOperation(value = "스크랩 처리")
	@PostMapping("/scrap/{projectindex}")
	public ResponseEntity<?> addScrap(@ApiParam(value = "게시글id만 path로 넘겨주시면 됩니다!! user정보는 토큰에서 가져옴") @PathVariable("projectindex") long projectIndex, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			service.addScrap(projectIndex, user);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("스크랩 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "스크랩 취소")
	@DeleteMapping("/scrap/{projectindex}")
	public ResponseEntity<?> deleteScrap(@ApiParam(value = "게시글id만 path로 넘겨주시면 됩니다!! user정보는 토큰에서 가져옴") @PathVariable("projectindex") long projectIndex, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			service.deleteScrap(projectIndex, user);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("스크랩취소 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "게시글 댓글달기")
	@PostMapping("/comment")
	public ResponseEntity<? extends BasicResponse> Comment(@ApiParam(value = "RequestBody에 json형식으로 코맨트정보만 넘기면됨. user정보는 토큰에서 가져옴") @RequestBody CommentDto.save commentDto, @AuthenticationPrincipal User user) throws FirebaseMessagingException {
		try {
			commentService.save(commentDto, user);
			Project push = projectRepo.findById(commentDto.getProjectId()).orElseThrow();
			if(push.getAuthor().isCommentPush()) {
				pushService.sendCommentToToken(push.getAuthor().getRegistrationToken());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("댓글 추가 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "댓글 삭제")
	@DeleteMapping("/comment/delete/{id}")
	public ResponseEntity<? extends BasicResponse> deleteComment(@ApiParam(value = "") @PathVariable("id")long commentId){
		try {
			commentService.delete(commentId);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("failed", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	
	@ApiOperation(value = "프로젝트 참가 요청")
	@PostMapping("/participant/{projectid}")
	public ResponseEntity<? extends BasicResponse> participantRequest(@ApiParam(value = "프로젝트 id") @PathVariable("projectid") long projectId , @AuthenticationPrincipal User user) throws FirebaseMessagingException {
		try {
			service.participantRequest(projectId, user);
			Project push = projectRepo.findById(projectId).orElseThrow();
			if(push.getAuthor().isRequestPush()) {
				pushService.sendParticipantRequestToToken(push.getAuthor().getRegistrationToken());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("failed", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "프로젝트 참가 요청 취소")
	@DeleteMapping("/participant/{projectid}")
	public ResponseEntity<? extends BasicResponse> participantRequestCancel(@ApiParam(value = "프로젝트 id") @PathVariable("projectid") long projectId , @AuthenticationPrincipal User user) {
		try {
			service.participantRequestCancel(projectId, user);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("failed", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "프로젝트 참가 수락")
	@PostMapping("/participant/accept")
	public ResponseEntity<? extends BasicResponse> participantAccept(@RequestBody RequestAcceptDto accept) throws FirebaseMessagingException {
		try {
			service.addParticipant(accept);
			User user = userRepo.findById(accept.getUserIndex()).orElseThrow();
			if(user.isRequestAcceptPush()) {
				pushService.sendParticipantAcceptToToken(user.getRegistrationToken());
			}
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("failed", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	

	@ApiOperation(value = "게시물 하나보기(상세)")
	@GetMapping("/api/projects/get/{id}")
	public ResponseEntity<? extends BasicResponse> getOne(@PathVariable("id") Long id) {
		ProjectDto projectPost;
		try {
			projectPost = service.findById(id);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 게시글 정보가 없습니다"));
		}
		return ResponseEntity.ok().body(new CommonResponse<ProjectDto>(projectPost));
	}
	

	@ApiOperation(value = "게시물 삭제")
	@DeleteMapping("/api/projects/delete/{id}")
	public ResponseEntity<? extends BasicResponse> delete(@PathVariable("id") Long id) {
		try {
			service.delete(id);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 게시글 정보가 없습니다"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "게시물 업데이트")
	@PutMapping("/api/projects/modify/{id}")
	public ResponseEntity<? extends BasicResponse> modify(@PathVariable("id") long id,  @RequestBody ProjectModifyDto modifyDto) {
		try {
			service.modify(id, modifyDto);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 게시글 정보가 없습니다"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "프로젝트 상태변경")
	@PostMapping("/api/projects/change/{id}")
	public ResponseEntity<? extends BasicResponse> changeStatus(@PathVariable("id") long id, @RequestParam("status") @ApiParam(value = "status") String status) {
		try {
			service.changeStatus(id,status);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 게시글 정보가 없습니다"));
		}
		
		return ResponseEntity.ok().body(new Response("success"));
	}
	
}
