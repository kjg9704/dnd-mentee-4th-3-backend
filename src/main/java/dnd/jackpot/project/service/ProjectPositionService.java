package dnd.jackpot.project.service;

import java.util.List;

import dnd.jackpot.project.dto.ProjectPositionDto;
import dnd.jackpot.project.entity.Project;

public interface ProjectPositionService {
	List<ProjectPositionDto> save(List<String> position, Project project);
	void removeByProject(Project project);
}
