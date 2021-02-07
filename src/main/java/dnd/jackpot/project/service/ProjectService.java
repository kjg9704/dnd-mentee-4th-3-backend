package dnd.jackpot.project.service;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.dto.ProjectSearchDto;

public interface ProjectService {
	PagingDto<ProjectDto> findAll(ProjectSearchDto searchDto);
	
	ProjectDto findById(Long id);
	ProjectDto save(ProjectSaveDto saveDto);
	void addScrap(int postId, long userIndex);
	
	ProjectDto findById(Long id);
	
}
