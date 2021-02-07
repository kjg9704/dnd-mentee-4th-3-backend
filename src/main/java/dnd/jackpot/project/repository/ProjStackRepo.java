package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.stack.entity.EstackProgrammer;

public interface ProjStackRepo extends JpaRepository<ProjectStack, Integer>{
//	List<ProjectStack> findAllByProject(Project project);
	
//	ProjectStack findByProjAndStack(Project project, EstackProgrammer stack);
}
