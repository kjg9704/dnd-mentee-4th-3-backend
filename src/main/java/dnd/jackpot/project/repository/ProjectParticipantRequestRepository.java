package dnd.jackpot.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectParticipant;
import dnd.jackpot.project.entity.ProjectParticipantRequest;
import dnd.jackpot.user.User;

public interface ProjectParticipantRequestRepository extends JpaRepository<ProjectParticipantRequest, Long> {

	List<ProjectParticipantRequest> findAllByAuthor(User author);
	
	List<ProjectParticipantRequest> findAllByUser(User user);

	List<ProjectParticipantRequest> findAllByProject(Project project);
	
}
