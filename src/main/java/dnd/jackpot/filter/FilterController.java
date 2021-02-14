package dnd.jackpot.filter;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSearchDto;
import dnd.jackpot.project.service.CommentService;
import dnd.jackpot.project.service.ProjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/filters")
public class FilterController {
	private final PFilterService filterService;
	
	@GetMapping("/projects")
	public PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto){
		return filterService.getAll(searchDto);
	}
	
//	@GetMapping("/members")
//	public List<MFilterDto> getAll(){
//		return filterService.getAll();
//	}
}
