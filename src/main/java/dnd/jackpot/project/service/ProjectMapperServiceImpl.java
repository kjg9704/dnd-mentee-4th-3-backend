package dnd.jackpot.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.entity.Project;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectMapperServiceImpl implements ProjectMapperService{
	//comment 추가하기
	private final ProjectStackService stackservice;
	private final ProjectPositionService positionservice;
	@Override
	public ProjectDto.filterDto toDto(Project project) {
		LocalDateTime createdDateTime = project.getCreatedAt();
//		String timeDesc = timeDescService.generate(createdDateTime);
		return buildDto(project,createdDateTime);
	}
	
	@Override
	public List<ProjectDto.filterDto> toDto(List<Project>projects){
		return projects.stream().map(project->toDto(project)).collect(Collectors.toList());
	}
	
	private ProjectDto.filterDto buildDto(Project project, LocalDateTime createdDateTime) {
		ProjectDto.filterDto dto = new ProjectDto.filterDto();
		dto.setId(project.getId());
		dto.setScrapped(project.getScrap().size());
		dto.setPosition(positionservice.getAllByProject(project));
		dto.setInterest(project.getInterest());
		dto.setStacks(stackservice.getAllByProject(project));
		dto.setTitle(project.getTitle());
		dto.setInterest(project.getInterest());
		return dto;
	}
}
