package dnd.jackpot.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.ProjectParticipantRequest;

public interface ProjectParticipantRequestRepository extends JpaRepository<ProjectParticipantRequest, Long> {

}
