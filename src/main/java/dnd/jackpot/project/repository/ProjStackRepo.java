package dnd.jackpot.project.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.entity.EstackProgrammer;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectInterest;
import dnd.jackpot.project.entity.ProjectStack;


public interface ProjStackRepo extends JpaRepository<ProjectStack, Long>{
	List<ProjectStack> findAllByProject(Project project);
	
//	ProjectStack findByProjAndStack(Project project, EstackProgrammer stack);
//	@Transactional
//	@Modifying
//	List<ProjectInterest> deleteAllByProject(Project project);

	@Transactional
	@Modifying
//	@Query("DELETE m from ProjectStack m where m.project = :project")
	List<ProjectStack> removeByProject(ProjectInterest project);

	@Transactional(readOnly = true)
	@Query("SELECT p FROM ProjectStack p WHERE p.stack IN (:stacks)")
	List<ProjectStack> findByStackIn(@Param("stacks") List<EstackProgrammer> stackFilter);
}
