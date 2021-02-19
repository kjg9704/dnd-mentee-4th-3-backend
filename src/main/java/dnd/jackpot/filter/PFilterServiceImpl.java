package dnd.jackpot.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSearchDto;
import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.EstackProgrammer;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.project.service.PagingMapper;
import dnd.jackpot.project.service.ProjectMapperService;
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
//	
	private List<ERegion> RprojectList;
	private List<EstackProgrammer> SprojectList;
	private List<Einterest> IprojectList;
	
	
	@Override 
	public PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto){
		Page<Project> pageProjects;

		if(searchDto.getStackFilter() != null) {
			SprojectList = new ArrayList<>();
			List<String> s = searchDto.getStackFilter();
			for(String stack : s) {
				EstackProgrammer stackProgram = EstackProgrammer.valueOf(stack);
				SprojectList.add(stackProgram);
			}
		}
		if(searchDto.getInterestFilter()!=null) {
			IprojectList = new ArrayList<>();
			for(String interest : searchDto.getInterestFilter()) {
				Einterest interests = Einterest.valueOf(interest);
				IprojectList.add(interests);
			}
		}
		if(searchDto.getRegionFilter()!=null) {
			RprojectList = new ArrayList<>();
			for(String region : searchDto.getRegionFilter()) {
				ERegion regions = ERegion.valueOf(region);
				RprojectList.add(regions);
			}
		}
		validateSearchDto(searchDto);
		Pageable pageable = PageRequest.of(searchDto.getPageNumber(), searchDto.getPageSize(),Direction.DESC,"createdAt");
		pageProjects = repo.findByRegionInAndInterestInAndStackIn(RprojectList, IprojectList, SprojectList, pageable);
		RprojectList=null;
		IprojectList=null;
		SprojectList=null;
		List<ProjectDto> projectDtoList = projectMapperService.toDto(pageProjects.getContent());
		return PagingMapper.map(pageProjects, projectDtoList);
		
	}
	
	@Override
	public PagingDto<simpleResponse> getAllUsers(UserSearchDto userSearchDto) {
	//	validateSearchDto(userSearchDto);
		Page<User> pageUsers;
		if(userSearchDto.getStackFilter() != null) {
			SprojectList = new ArrayList<>();
			List<String> s = userSearchDto.getStackFilter();
			for(String stack : s) {
				EstackProgrammer stackProgram = EstackProgrammer.valueOf(stack);
				SprojectList.add(stackProgram);
			}
		}
		
		if(userSearchDto.getRegionFilter()!=null) {
			RprojectList = new ArrayList<>();
			for(String region : userSearchDto.getRegionFilter()) {
				ERegion regions = ERegion.valueOf(region);
				RprojectList.add(regions);
			}
		}
		
		Pageable pageable = PageRequest.of(userSearchDto.getPageNumber(), userSearchDto.getPageSize(),Direction.DESC,"createdAt");
		pageUsers = userRepo.findAllByRegionInAndStackInAndPosition(RprojectList, SprojectList, userSearchDto.getPosition(), pageable);
		RprojectList=null;
		IprojectList=null;
		SprojectList=null;
		List<simpleResponse> userDtoList = projectMapperService.toDto(pageProjects.getContent());
		return null;
	}
	
	private void validateSearchDto(ProjectSearchDto searchDto) {
		Integer pageSize = searchDto.getPageSize();
		Integer pageNumber = searchDto.getPageNumber();
//		if(Objects.isNull(pageSize)||Objects.isNull(pageNumber)) {
//			throw new CustomException(HttpStatus.BAD_REQUEST, "pageSize 또는 pageNumber이 null입니다.")
//		}
//		if(pageSize <= 0) {
//			throw new CustomException(HttpStatus.BAD_REQUEST, "PageSize는 1이상이어야 합니다.")
//		}
	}
	
	
}