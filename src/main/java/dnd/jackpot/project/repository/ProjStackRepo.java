package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectInterest;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.stack.entity.EstackProgrammer;

public interface ProjStackRepo extends JpaRepository<ProjectStack, Long>{
	List<ProjectInterest> findAllByProject(Project project);
	
//	ProjectStack findByProjAndStack(Project project, EstackProgrammer stack);
//	@Transactional
//	@Modifying
//	List<ProjectInterest> deleteAllByProject(Project project);

	@Transactional
	@Modifying
//	@Query("DELETE m from ProjectStack m where m.project = :project")
	List<ProjectInterest> removeByProject(ProjectInterest project);
}
