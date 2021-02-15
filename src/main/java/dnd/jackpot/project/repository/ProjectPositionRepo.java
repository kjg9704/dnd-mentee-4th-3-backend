package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectPosition;

public interface ProjectPositionRepo extends JpaRepository<ProjectPosition, Long>{
	List<ProjectPosition> findAllByProject(Project project);
	List<ProjectPosition> deleteAllByProject(Project project);
}
