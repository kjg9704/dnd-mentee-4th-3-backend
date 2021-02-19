package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Comment;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByProject(Project project);
	

}
