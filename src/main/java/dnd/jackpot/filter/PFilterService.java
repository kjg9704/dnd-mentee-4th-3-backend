package dnd.jackpot.filter;

import java.util.List;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSearchDto;

public interface PFilterService {
	PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto);
}
