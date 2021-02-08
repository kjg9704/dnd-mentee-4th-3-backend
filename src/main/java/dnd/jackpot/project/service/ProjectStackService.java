package dnd.jackpot.project.service;

import java.util.List;

import dnd.jackpot.project.dto.ProjectStackDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;

//import dnd.jackpot.project.entity.ProjectStack;


public interface ProjectStackService {
//	List<StackDto> getAllByFeed(EstackProgrammer stack);

	List<ProjectStackDto> save(List<String> stacks, Project project);

//	List<StackDto> getAllByProject(Project project);

//	void deleteByProject(Project project);
//
//	void deleteByImgFileIds(Feed persist, List<Long> removeImgFileIds);

	void removeByProject(Project project);

}
