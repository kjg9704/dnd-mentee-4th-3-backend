package dnd.jackpot.project.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dnd.jackpot.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
	private final ProjectService service;
	
//	@Secured("ROLE_USER")
	@PostMapping("")
	public ProjectDto save(ProjectSaveDto saveDto) {
		return service.save(saveDto);
	}
	
////	@Secured("ROLE_USER")
//	@GetMapping("")
//	public PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto){
//		return service.findAll(searchDto);
//	}
	
//	@Secured("ROLE_USER")
	@GetMapping("/{id}")
	public ProjectDto getOne(@PathVariable("id") Long id) {
		return service.findByID(id);
	}
	
////	@Secured("ROLE_USER")
//	@DeleteMapping("/{id}")
//	public ProjectDto delete(@PathVariable("id") Long id) {
//		return service.delete(id);
//	}
	
////	@Secured("ROLE_USER")
//	@PutMapping("/{id}")
//	public ProjectDto modify(@PathVariable("id") Long id, ProjectModifyDto modifyDto) {
//		return service.modify(id, modifyDto);
//	}
}
