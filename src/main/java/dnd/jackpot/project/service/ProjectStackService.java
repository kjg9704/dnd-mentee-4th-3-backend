package dnd.jackpot.project.service;

import java.util.List;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.stack.entity.EstackProgrammer;
//import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.stack.entity.StackDto;

public interface ProjectStackService {
//	List<StackDto> getAllByFeed(EstackProgrammer stack);

	List<StackDto> save(List<String> stacks, Project project);

//	List<StackDto> getAllByProject(Project project);

//	void deleteByProject(Project project);
//
//	void deleteByImgFileIds(Feed persist, List<Long> removeImgFileIds);

	void removeByProject(Project project);

}
