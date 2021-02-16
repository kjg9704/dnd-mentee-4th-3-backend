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
	
	void addScrap(int postId, long userIndex);
	

	
}
