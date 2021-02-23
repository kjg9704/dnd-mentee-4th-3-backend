package dnd.jackpot.project.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectScrap;
import dnd.jackpot.user.User;


public interface ProjectScrapRepository extends JpaRepository<ProjectScrap, Long>{

	List<ProjectScrap> findAllByUser(User user);

	void deleteByProjectAndUser(Project project,User user);
}
