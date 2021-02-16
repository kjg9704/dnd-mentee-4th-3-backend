package dnd.jackpot.project.service;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.user.User;

public interface ProjectService {
//	PagingDto<ProjectDto> findAll(ProjectSearchDto searchDto);
	
	ProjectDto findById(Long id);
	
	ProjectDto save(ProjectSaveDto saveDto, User user);
	
	ProjectDto delete(Long id);
	
	ProjectDto modify(Long id, ProjectModifyDto modifyDto);
	
	void addScrap(long postId, User user);
	
	void addParticipant(long requestId);
	
	void participantRequest(long projectId, User user);
	
}
