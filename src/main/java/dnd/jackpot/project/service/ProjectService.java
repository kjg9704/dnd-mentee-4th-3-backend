package dnd.jackpot.project.service;

import java.util.List;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectParticipantRequestDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.dto.RequestAcceptDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto.simpleResponse;

public interface ProjectService {
//	PagingDto<ProjectDto> findAll(ProjectSearchDto searchDto);
	
	ProjectDto findById(Long id);
	
	ProjectDto save(ProjectSaveDto saveDto, User user);
	
	ProjectDto delete(Long id);
	
	ProjectDto modify(Long id, ProjectModifyDto modifyDto);
	
	List<ProjectDto> findAllByAuthor(User Author);
	
	List<ProjectDto> findAllByParticipant(User user);
	
	List<ProjectParticipantRequestDto> findAllByRequestAuthor(User Author);
	
	void addScrap(long postId, User user);
	
	void addParticipant(RequestAcceptDto accept);
	
	void participantRequest(long projectId, User user);
	
	void participantRequestCancel(long projectId, User user);

	List<simpleResponse> getParticipant(Project project);

	List<simpleResponse> getRequest(Project project);

	void changeStatus(long id, String status);

	void deleteScrap(long projectIndex, User user);
	
}
