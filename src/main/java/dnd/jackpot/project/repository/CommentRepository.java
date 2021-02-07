package dnd.jackpot.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
