package dnd.jackpot.project.service;

import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;

public interface ProjectService {
//	PagingDto<ProjectDto> findAll(ProjectSearchDto searchDto);
	
	ProjectDto save(ProjectSaveDto saveDto);
	
}
