package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectInterest;

public interface ProjInterestRepo extends JpaRepository<ProjectInterest, Long >{
	
	List<ProjectInterest> findAllByProject(Project project);
	
	List<ProjectInterest> deleteAllByProject(Project project);
	
	

}
