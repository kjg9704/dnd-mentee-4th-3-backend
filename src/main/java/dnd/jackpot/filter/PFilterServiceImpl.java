package dnd.jackpot.filter;

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
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.project.service.PagingMapper;
import dnd.jackpot.project.service.ProjectMapperService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PFilterServiceImpl implements PFilterService {
	private final ProjectRepository repo;
	private final ProjectMapperService projectMapperService;
//	
//	private List<Project> RprojectList;
//	private List<Project> SprojectList;
//	private List<Project> IprojectList;
	
	
	@Override 
	public PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto){
		Page<Project> pageProjects;
//		if(Objects.nonNull(searchDto.getRegions())) {
////			 = ProjectMapperService.toDto(pageProjects.getContent());
////			repo.findAll()->
//			RprojectList = repo.findByRegion(searchDto.getRegions());
//		}else {
//			RprojectList = repo.findAll();
////			필터를 적용하지 않는경우 다 가져온다.
////			RprojectDtoList = get
//		}
//		if(Objects.nonNull(searchDto.getStacks())) {
////			 = ProjectMapperService.toDto(pageProjects.getContent());
//			SprojectList = repo.findByStacks();
//		}else {
//			SprojectList = repo.findAll();
//		}
//		if(Objects.nonNull(searchDto.getInterests())) {
//			IprojectList = repo.findByInterest();
//		}else {
//			IprojectList = repo.findAll();
//		}
//		RprojectList.retainAll(IprojectList);
//		RprojectList.retainAll(IprojectList);
		validateSearchDto(searchDto);
		Pageable pageable = PageRequest.of(searchDto.getPageNumber(), searchDto.getPageSize(),Direction.DESC,"createdAt");
//		pageProjects = repo.findByRegionInAndInterestInAndStacksIn(searchDto.getRegionFilter(), searchDto.getInterestFilter(), searchDto.getStackFilter(),pageable);
		pageProjects = repo.findByRegionIn(searchDto.getRegionFilter(), pageable);
		List<ProjectDto> projectDtoList = projectMapperService.toDto(pageProjects.getContent());
		return PagingMapper.map(pageProjects, projectDtoList);
		
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
