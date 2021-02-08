package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.stack.entity.EstackProgrammer;

public interface ProjStackRepo extends JpaRepository<ProjectStack, Long>{
	List<ProjectStack> findAllByProject(Project project);
	
//	ProjectStack findByProjAndStack(Project project, EstackProgrammer stack);
	@Transactional
	@Modifying
	List<ProjectStack> deleteAllByProject(Project project);

	@Transactional
	@Modifying
//	@Query("DELETE m from ProjectStack m where m.project = :project")
	List<ProjectStack> removeByProject(ProjectStack project);
}
