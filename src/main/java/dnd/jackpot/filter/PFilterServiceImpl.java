package dnd.jackpot.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectDto.filterDto;
import dnd.jackpot.project.dto.ProjectSearchDto;
import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.Estack;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.project.repository.ProjStackRepo;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.project.service.PagingMapper;
import dnd.jackpot.project.service.ProjectMapperService;
import dnd.jackpot.security.JwtUserDetailsService;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto.profileResponse;
import dnd.jackpot.user.UserDto.simpleResponse;
import dnd.jackpot.user.UserRepository;
import dnd.jackpot.user.UserSearchDto;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PFilterServiceImpl implements PFilterService {
	private final ProjectRepository repo;
	private final ProjectMapperService projectMapperService;
	private final UserRepository userRepo;
	private final JwtUserDetailsService userService;
//	
	private ERegion RprojectList;
	private List<Estack> SprojectList;
	private List<Einterest> IprojectList;
	private List<String> DprojectList;
	private String PprojectList;
	private final ProjStackRepo stackrepo;

	@Override 
	@Transactional
	public PagingDto<filterDto> getAll(ProjectSearchDto searchDto){
		Page<Project> pageProjects = null;

		if(searchDto.getStackFilter().get(0)!="") {
			SprojectList = new ArrayList<>();
			List<String> s = searchDto.getStackFilter();
			for(String stack : s) {
				Estack stackProgram = Estack.valueOf(stack);
				SprojectList.add(stackProgram);
			}
		}
		if(searchDto.getInterestFilter().get(0)!="") {
			IprojectList = new ArrayList<>();
			for(String interest : searchDto.getInterestFilter()) {
				Einterest interests = Einterest.valueOf(interest);
				IprojectList.add(interests);
			}
		}
		if(!searchDto.getRegionFilter().isEmpty()) {
			RprojectList = ERegion.valueOf(searchDto.getRegionFilter());
		}
		if(searchDto.getDuration().get(0)!="") {
			DprojectList = new ArrayList<>();
			for(String duration : searchDto.getDuration()) {
				DprojectList.add(duration);
			}
		}
		validateSearchDto(searchDto);
		Pageable pageable = PageRequest.of(searchDto.getPageNumber(), searchDto.getPageSize(),Direction.DESC, searchDto.getSortType().getName());
		if(searchDto.getSortType().getName()=="createdAt") {
			pageProjects = repo.findByRegionInAndInterestInAndStackInORDERBYdate(RprojectList, IprojectList, SprojectList, DprojectList ,pageable);
		}
		if(searchDto.getSortType().getName()=="scrappedNum") {
			pageProjects = repo.findByRegionInAndInterestInAndStackInORDERBYpopular(RprojectList, IprojectList, SprojectList, DprojectList ,pageable);
		} 
		RprojectList=null;
		IprojectList=null;
		SprojectList=null;
		List<ProjectDto.filterDto> filterDtoList = projectMapperService.toDto(pageProjects.getContent());
		return PagingMapper.map(pageProjects, filterDtoList);
		
	}
	
	@Override
	public PagingDto<simpleResponse> getAllUsers(UserSearchDto userSearchDto) {
	//	validateSearchDto(userSearchDto);
		Page<User> pageUsers = null;
		if(userSearchDto.getStackFilter().get(0)!="") {
			SprojectList = new ArrayList<>();
			List<String> s = userSearchDto.getStackFilter();
			for(String stack : s) {
				Estack stackProgram = Estack.valueOf(stack);
				SprojectList.add(stackProgram);
			}
		}
		
		if(!userSearchDto.getRegionFilter().isEmpty()) {
			RprojectList = ERegion.valueOf(userSearchDto.getRegionFilter());
		}
		if(!userSearchDto.getPosition().isEmpty()) {
			PprojectList = userSearchDto.getPosition();
		}
		
		Pageable pageable = PageRequest.of(userSearchDto.getPageNumber(), userSearchDto.getPageSize());
		if(userSearchDto.getSortType().getName().equals("scrappedNum")) {
			pageUsers = userRepo.findAllByRegionInAndStackInAndPositionORDERBYsize(RprojectList, SprojectList, PprojectList ,pageable);
		} 
		if(userSearchDto.getSortType().getName().equals("createdAt")) {
			pageUsers = userRepo.findAllByRegionInAndStackInAndPositionORDERBYcreated(RprojectList, SprojectList, PprojectList ,pageable);
		}
		RprojectList=null;
		PprojectList=null;
		SprojectList=null;
		List<simpleResponse> userDtoList = userService.userListMapper(pageUsers.getContent());
		return PagingMapper.mapUser(pageUsers, userDtoList);
	}
	
	private void validateSearchDto(ProjectSearchDto searchDto) {
		Integer pageSize = searchDto.getPageSize();
		Integer pageNumber = searchDto.getPageNumber();
//		if(Objects.isNull(pageSize)||Objects.isNull(pageNumber)) {
//			throw new CustomException(HttpStatus.BAD_REQUEST, "pageSize 또는 pageNumber이 null입니다.")
//		}s
//		if(pageSize <= 0) {
//			throw new CustomException(HttpStatus.BAD_REQUEST, "PageSize는 1이상이어야 합니다.")
//		}
	}
	
	
}