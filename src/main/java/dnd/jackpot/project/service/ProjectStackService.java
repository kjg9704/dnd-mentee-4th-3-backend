package dnd.jackpot.project.service;

import java.util.List;

import dnd.jackpot.project.dto.ProjectStackDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;

//import dnd.jackpot.project.entity.ProjectStack;


public interface ProjectStackService {

	List<ProjectStackDto> save(List<String> stacks, Project project);

	List<String> getAllByProject(Project project);

//	void deleteByProject(Project project);
//
//	void deleteByImgFileIds(Feed persist, List<Long> removeImgFileIds);

	void removeByProject(Project project);

}
