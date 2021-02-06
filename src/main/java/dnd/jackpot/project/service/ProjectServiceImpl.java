package dnd.jackpot.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectMapper;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.stack.entity.EstackProgrammer;
import dnd.jackpot.stack.entity.StackDto;
//import dnd.jackpot.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
	
	private final ProjectRepository repo;
	private final ProjectStackService projectStackService;

	@Override
	@Transactional
	public ProjectDto save(ProjectSaveDto saveDto) {
//		User author = securityService.getLoggedUser();
//		나중에 이부분 바꾸기
		String title;
		Project project = ProjectMapper.map(saveDto);
//		ProjectStack projStack = ProjectStack.of(project, saveDto.getStacks())
		projectStackService.save(saveDto.getStacks(),project);
		repo.save(project);
		return toDto(project);//with comments 필요한지..
	}
	
	private ProjectDto toDto(Project project) {
//		ProjectStack stack = projectStackService.getAllByProject(project);
//		List<StackDto> stackDtos = ProjectStackService.getAllByProject(project);
		LocalDateTime createdDateTime = project.getCreatedAt();
		return ProjectMapper.map(project, createdDateTime);//stackDtos
	}
	
}
