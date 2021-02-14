package dnd.jackpot.project.service;

import java.util.List;

import org.springframework.stereotype.Component;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.entity.Project;

public interface ProjectMapperService {

	ProjectDto toDto(Project project);
	List<ProjectDto> toDto(List<Project>project);
}
