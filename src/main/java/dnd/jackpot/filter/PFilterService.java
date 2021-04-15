package dnd.jackpot.filter;

import java.util.List;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectDto.filterDto;
import dnd.jackpot.project.dto.ProjectSearchDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.user.UserDto;
import dnd.jackpot.user.UserSearchDto;

public interface PFilterService {
	PagingDto<filterDto> getAll(ProjectSearchDto searchDto);
	
	PagingDto<UserDto.simpleResponse> getAllUsers(UserSearchDto userSearchDto);
}
