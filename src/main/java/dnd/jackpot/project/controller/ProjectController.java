package dnd.jackpot.project.controller;

import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dnd.jackpot.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.dto.ProjectSearchDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
	private final ProjectService service;
	
//	@Secured("ROLE_USER")
	@PostMapping("")
	public ProjectDto save(@RequestBody ProjectSaveDto saveDto) {
		return service.save(saveDto);
	}
	
	@PostMapping("/scrap/{projectindex}")
	public ResponseEntity<?> addScrap(@PathVariable("projectindex") int projectIndex, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		service.addScrap(projectIndex, user.getUserIndex());
		return ResponseEntity.ok("success");
	}
	
	
////	@Secured("ROLE_USER")
	@GetMapping("")
	public PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto){
		return service.findAll(searchDto);
	}
	
//	@Secured("ROLE_USER")
	@GetMapping("/{id}")
	public ProjectDto getOne(@PathVariable("id") Long id) {
		return service.findById(id);
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
