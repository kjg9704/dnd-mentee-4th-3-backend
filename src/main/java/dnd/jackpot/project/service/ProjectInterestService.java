package dnd.jackpot.project.service;

import java.util.List;

import dnd.jackpot.project.dto.ProjectInterestDto;
import dnd.jackpot.project.entity.Project;

public interface ProjectInterestService {
	List<ProjectInterestDto> save(List<String> interests, Project project);
	void removeByProject (Project project);
}
