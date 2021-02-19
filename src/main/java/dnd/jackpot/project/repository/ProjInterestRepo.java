package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.project.entity.Estack;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectInterest;

public interface ProjInterestRepo extends JpaRepository<ProjectInterest, Long >{
	
	List<ProjectInterest> findAllByProject(Project project);
	
	List<ProjectInterest> deleteAllByProject(Project project);
	
	@Query("SELECT p FROM ProjectInterest p WHERE p.interest IN (:interests)")
	List<ProjectInterest> findByInterestIn(@Param("interests") List<Einterest> interestFilter);

}
