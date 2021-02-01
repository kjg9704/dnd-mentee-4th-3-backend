package dnd.jackpot.project.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	
}
