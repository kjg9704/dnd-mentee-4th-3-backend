package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectParticipant;
import dnd.jackpot.user.User;

public interface ProjectParticipantRepository extends JpaRepository<ProjectParticipant, Long> {

	List<ProjectParticipant> findAllByUser(User user);

	List<ProjectParticipant> findAllByProject(Project project);
}
