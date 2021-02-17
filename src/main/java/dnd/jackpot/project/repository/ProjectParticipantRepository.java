package dnd.jackpot.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dnd.jackpot.project.entity.ProjectParticipant;

public interface ProjectParticipantRepository extends JpaRepository<ProjectParticipant, Long> {

}
