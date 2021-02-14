package dnd.jackpot.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.entity.Project;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectMapperServiceImpl implements ProjectMapperService{
	//comment 추가하기
	@Override
	public ProjectDto toDto(Project project) {
		LocalDateTime createdDateTime = project.getCreatedAt();
//		String timeDesc = timeDescService.generate(createdDateTime);
		return buildDto(project);
	}
	
	@Override
	public List<ProjectDto> toDto(List<Project>projects){
		return projects.stream().map(project->toDto(project)).collect(Collectors.toList());
	}
	
	private ProjectDto buildDto(Project project) {
		ProjectDto dto = new ProjectDto();
		dto.setId(project.getId());
		dto.setShortDesc(project.getShortDesc());
//		dto.setCreatedDateTime(project.getCreatedAt());
		dto.setTitle(project.getTitle());
		return dto;
	}
}
