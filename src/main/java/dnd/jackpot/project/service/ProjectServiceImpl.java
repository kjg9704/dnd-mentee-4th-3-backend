package dnd.jackpot.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectSaveDto;

import dnd.jackpot.project.dto.ProjectSearchDto;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectMapper;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.project.entity.Scrap;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.project.repository.ScrapRepository;
import dnd.jackpot.stack.entity.EstackProgrammer;

//import dnd.jackpot.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
	
	private final ProjectRepository repo;
	private final ProjectStackService projectStackService;
	private final ProjectInterestService projectInterestService;
	private final ScrapRepository scrapRepo;

//	public PagingDto<ProjectDto> findAll (ProjectSearchDto searchDto){
		
//		validateSearchDto(searchDto);
//		Pageable pageable = PageRequest.of(searchDto.getPageNumber(),searchDto.getPageSize(),Direction.DESC, "createdAt");
//		Page<Project> pageProjects;
////		if(searchDto.)
//		List<ProjectDto> FeedDtoList = ProjectMapperService.toDto(pageProjects.getContent());
//		return FeedDtoList;
//	}
//	private void validateSearchDto(ProjectSearchDto searchDto) {
//		Integer pageSize = searchDto.getPageSize();
//		Integer pageNumber = searchDto.getPageNumber();
//		if(Objects.isNull(pageSize)||Objects.isNull(pageNumber)) {
//			throw new CustomException(HttpStatus.BAD_REQUEST, "pageSize 또는 PageNumber Null입니다.")
//		}if(pageSize<=0) {
//			throw new CustomException(HttpStatus.BAD_REQUEST,"PAGE 1보다 작음" )
//		}
//	}
	
	@Override
	@Transactional
	public ProjectDto save(ProjectSaveDto saveDto) {
//		User author = securityService.getLoggedUser();
//		나중에 이부분 바꾸기
		String title;
		Project project = ProjectMapper.map(saveDto);
//		ProjectStack projStack = ProjectStack.of(project, saveDto.getStacks())
		projectStackService.save(saveDto.getStacks(),project);
		projectInterestService.save(saveDto.getInterest(),project);
		repo.save(project);
		return toDto(project);//with comments 필요한지..
	}
	
	private ProjectDto toDto(Project project) {
//		ProjectStack stack = projectStackService.getAllByProject(project);
//		List<StackDto> stackDtos = ProjectStackService.getAllByProject(project);
		LocalDateTime createdDateTime = project.getCreatedAt();
		return ProjectMapper.map(project, createdDateTime);//stackDtos
	}
	@Override
	@Transactional(readOnly = true)
	public ProjectDto findById(Long id) {
		Project project = repo.findById(id).orElseThrow();
		return toDto(project);
	}
	
	@Override
	public void addScrap(int projectId, long userIndex) {
		scrapRepo.save(Scrap.builder()
				.postIndex(projectId)
				.userIndex(userIndex)
				.build());
	}
	
	@Override
	@Transactional
	public ProjectDto delete (Long id) {
		Project project = repo.findById(id).orElseThrow();
		ProjectDto projectDto = new ProjectDto();
		projectDto.setId(project.getId());
		repo.delete(project);
		return projectDto;
	}
	
	@Override
	public ProjectDto modify(Long id, ProjectModifyDto modifyDto) {
		Project project = repo.findById(id).orElseThrow();
		project.update(modifyDto.getTitle(), modifyDto.getShortdesc(),modifyDto.getRegion());
		if(Objects.nonNull(modifyDto.getStack())) {
			projectStackService.removeByProject(project);
			projectStackService.save(modifyDto.getStack(), project);
		}
		if(Objects.nonNull(modifyDto.getInterest())) {
			projectInterestService.removeByProject(project);
			projectInterestService.save(modifyDto.getInterest(), project);
		}
		repo.save(project);
		return toDto(project);
		
	}
	
}
