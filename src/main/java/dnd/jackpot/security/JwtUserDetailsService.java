package dnd.jackpot.security;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectStackDto;
import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Estack;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.project.repository.ProjectScrapRepository;
import dnd.jackpot.project.service.ProjectService;
import dnd.jackpot.project.repository.ProjectScrapRepository;
import dnd.jackpot.user.DeletedUser;
import dnd.jackpot.user.DeletedUserRepository;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto;
import dnd.jackpot.user.UserDto.otherResponse;
import dnd.jackpot.user.UserDto.simpleResponse;
import dnd.jackpot.user.UserModifyDto;
import dnd.jackpot.user.UserRepository;
import dnd.jackpot.user.UserScrap;
import dnd.jackpot.user.UserScrapRepository;
import dnd.jackpot.user.UserStacks;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	private final DeletedUserRepository deletedUserRepository;
	private final ProjectScrapRepository projectScrapRepository;
	private final UserScrapRepository userScrapRepository;
	private final ProjectService projectService;


	public UserDetails loadUserByEmailAndLogintype(String email, String loginType) throws UsernameNotFoundException {
		System.out.println("----이게진짜 유저정보가져옴");
		System.out.println(email);
		System.out.println(loginType);
		return userRepository.findByEmailAndLogintype(email, loginType)
				.orElseThrow(() -> new UsernameNotFoundException((email)));
	}
	
	public User loadUserByUserIndex(long id) {
		return userRepository.findById(id).orElseThrow();
	}
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현해야하나 우리는 email중복이 가능하고 (email + logintype) 으로 회원을 특정하기때문에 필요없음.
		System.out.println("---아이디비번찾으면서 호출됨");
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException((email)));
	}
	/**
	 * 회원정보 저장
	 *
	 * @param infoDto 회원정보가 들어있는 DTO
	 * @return 회원번호 PK
	 * @throws Exception 
	 */
	@Transactional
	public Long save(UserDto infoDto) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		infoDto.setPassword(encoder.encode(infoDto.getPassword()));
		LocalDate date = LocalDate.now();
		ERegion region = ERegion.valueOf(infoDto.getRegion());
		boolean check = deletedUserRepository.existsByUserEmailAndLoginType(infoDto.getEmail(), infoDto.getLoginType());
		if(!check) {
			User user = userRepository.save(User.builder()
					.email(infoDto.getEmail())
					.auth(infoDto.getAuth())
					.name(infoDto.getName())
					.region(region)
					.position(infoDto.getPosition())
					.logintype(infoDto.getLoginType())
					.career(infoDto.getCareer())
					.date(date.toString())
					.privacy(infoDto.isPrivacy())
					.emoticon(infoDto.getEmoticon())
					.introduction(infoDto.getIntroduction())
					.portfolioLink1(infoDto.getPortfolioLink1())
					.portfolioLink2(infoDto.getPortfolioLink2())
					.password(infoDto.getPassword()).build());
			for(int i = 0; i < infoDto.getStacks().size(); i++) {
				user.addStacks(infoDto.getStacks().get(i));
			}
			return user.getUserIndex();
		}
		else {
			Exception exception = new Exception();
			throw exception;
		}
	}
		
	
	@Transactional
	public Long modifyUser(UserModifyDto infoDto, User user) {
		User persistenceUser = (User) loadUserByEmailAndLogintype(user.getEmail(), user.getLoginType());
			persistenceUser.update(infoDto);
			persistenceUser = userRepository.save(persistenceUser);
			for(int i = 0; i < infoDto.getStacks().size(); i++) {
				persistenceUser.addStacks(infoDto.getStacks().get(i));
			}
		return persistenceUser.getUserIndex();
	}
	
	@Transactional
	public void deletedSave(String email, String loginType) {
		deletedUserRepository.save(DeletedUser.builder()
				.userEmail(email)
				.loginType(loginType)
				.build());
		return;
	}
	
	@Transactional
	public void modifyPassword(String email, String password) {
		User user = (User) loadUserByEmailAndLogintype(email, "normal");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		userRepository.save(user);
	}
	
	@Transactional
	public void modifyPassword(String password, User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		userRepository.save(user);
	}
	
	@Transactional
	public void modifyRegistrationToken(String token, User user) {
		user.setRegistrationToken(token);
		userRepository.save(user);
	}
	
	public Boolean isExistEmail(String email) {
	    return userRepository.existsByEmail(email);
	  }
	
	public Boolean isExistName(String name) {
	    return userRepository.existsByName(name);
	  }
	
	public String getLoginType(User user) {
		return 	user.getLoginType();
	}
	
	public List<simpleResponse> userListMapper(List<User> users){
		List<simpleResponse> simpleList = new ArrayList<>();
		for(User user : users) {
			simpleList.add(new simpleResponse(user.getUserIndex(), user.getRegion(), user.getPosition(), user.getCareer(), user.getEmoticon()));
		}
		return simpleList;
	}
	
	@Transactional
	public void deleteUser(String email, String loginType) {
		userRepository.deleteByEmailAndLogintype(email, loginType);
	}
	
	@Transactional
	public void addUserScrap(long userIndex, User user) {
		User scrapedUser = userRepository.findById(userIndex).orElseThrow();
		userScrapRepository.save(UserScrap.builder()
				.scrapedUser(scrapedUser)
				.scrappingUser(user.getUserIndex())
				.build());
	}
	
	@Transactional
	public void deleteUserScrap(long userIndex, User user) {
		User scrapedUser = userRepository.findById(userIndex).orElseThrow();
		userScrapRepository.deleteByUserAndScrappingUser(scrapedUser, user.getUserIndex());
	}
	
	public List<otherResponse> getScrappingUsers(User user) {
		List<otherResponse> resultList = new ArrayList<>();
		List<UserScrap> scrapList = userScrapRepository.findAllByScrappingUser(user.getUserIndex());
		for(UserScrap scrap : scrapList) {
			User showuser = userRepository.findById(scrap.getUser().getUserIndex()).orElseThrow();
			List<Estack> stacks = new ArrayList<Estack>();
			List<ProjectDto> projects = projectService.findAllByAuthor(showuser);
			List<ProjectDto> participantList = projectService.findAllByParticipant(showuser);
			for(UserStacks st : showuser.getStacks()) {
				stacks.add(st.getStack());
			}
			resultList.add(new otherResponse(showuser.getName(), showuser.getRegion(), showuser.getPosition(), stacks, showuser.isPrivacy(), showuser.getCareer(), showuser.getAuth(), showuser.getEmoticon(), showuser.getIntroduction(),
					showuser.getPortfolioLink1(), showuser.getPortfolioLink2(), projects, participantList));
		}
		return resultList;
	}
	
}
