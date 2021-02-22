package dnd.jackpot.filter;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSearchDto;
import dnd.jackpot.project.service.CommentService;
import dnd.jackpot.project.service.ProjectService;
import dnd.jackpot.user.UserDto;
import dnd.jackpot.user.UserSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(description = "필터 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filters")
public class FilterController {
	private final PFilterService filterService;
	
	@ApiOperation(value = "프로젝트 필터")
	@PostMapping("/projects")
	public PagingDto<ProjectDto.filterDto> getAll(@RequestBody ProjectSearchDto searchDto){
		return filterService.getAll(searchDto);
	}
	@ApiOperation(value = "멤버 필터")
	@PostMapping("/users")
	public PagingDto<UserDto.simpleResponse> getUsers(@RequestBody UserSearchDto searchDto){
		return filterService.getAllUsers(searchDto);
	}
	
}
