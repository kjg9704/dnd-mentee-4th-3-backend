package dnd.jackpot.security;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import dnd.jackpot.user.DeletedUser;
import dnd.jackpot.user.DeletedUserRepository;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto;
import dnd.jackpot.user.UserModifyDto;
import dnd.jackpot.user.UserRepository;

import java.time.LocalDate;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	private final DeletedUserRepository deletedUserRepository;


	public UserDetails loadUserByEmailAndLogintype(String email, String loginType) throws UsernameNotFoundException {
		System.out.println("----이게진짜 유저정보가져옴");
		System.out.println(email);
		System.out.println(loginType);
		return userRepository.findByEmailAndLogintype(email, loginType)
				.orElseThrow(() -> new UsernameNotFoundException((email)));
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
	 */
	@Transactional
	public Long save(UserDto infoDto) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		infoDto.setPassword(encoder.encode(infoDto.getPassword()));
		LocalDate date = LocalDate.now();
		User user = userRepository.save(User.builder()
				.email(infoDto.getEmail())
				.auth(infoDto.getAuth())
				.name(infoDto.getName())
				.region(infoDto.getRegion())
				.job(infoDto.getJob())
				.logintype(infoDto.getLoginType())
				.career(infoDto.getCareer())
				.date(date.toString())
				.privacy(infoDto.isPrivacy())
				.password(infoDto.getPassword()).build());
		for(int i = 0; i < infoDto.getStacks().size(); i++) {
			user.addStacks(infoDto.getStacks().get(i));
		}
		return user.getUserIndex();
	}
	
	@Transactional
	public Long modifyUser(UserModifyDto infoDto, User user) {
			user.update(infoDto);
			User persistenceUser = userRepository.save(user);
			for(int i = 0; i < infoDto.getStacks().size(); i++) {
				persistenceUser.addStacks(infoDto.getStacks().get(i));
			}
		return persistenceUser.getUserIndex();
	}
	
	@Transactional
	public void deletedSave(String email, String loginType) {
		DeletedUser user = deletedUserRepository.save(DeletedUser.builder()
				.userEmail(email)
				.loginType(loginType)
				.build());
	}
	
	public void modifyPassword(String email, String password) {
		User user = (User) loadUserByEmailAndLogintype(email, "normal");
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		userRepository.save(user);
	}
	
	public void modifyPassword(String password, User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
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
	
	@Transactional
	public void deleteUser(String email, String loginType) {
		userRepository.deleteByEmailAndLogintype(email, loginType);
	}
	
}
