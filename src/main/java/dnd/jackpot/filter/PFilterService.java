package dnd.jackpot.filter;

import java.util.List;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSearchDto;
import dnd.jackpot.user.UserDto;
import dnd.jackpot.user.UserSearchDto;

public interface PFilterService {
	PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto);
	
	PagingDto<UserDto.simpleResponse> getAllUsers(UserSearchDto userSearchDto);
}
