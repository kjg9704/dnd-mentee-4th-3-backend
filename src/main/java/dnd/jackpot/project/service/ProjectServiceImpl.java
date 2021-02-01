package dnd.jackpot.project.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectMapper;
import dnd.jackpot.project.repository.ProjectRepository;
//import dnd.jackpot.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
	
	private final ProjectRepository repo;

	@Override
	@Transactional
	public ProjectDto save(ProjectSaveDto saveDto) {
//		User author = securityService.getLoggedUser();
//		나중에 이부분 바꾸기
		String title;
		Project project = ProjectMapper.map(saveDto);
		repo.save(project);
		return toDto(project);//with comments 필요한지..
	}
	private ProjectDto toDto(Project project) {
		LocalDateTime createdDateTime = project.getCreatedAt();
		return ProjectMapper.map(project, createdDateTime);
	}
	
}
