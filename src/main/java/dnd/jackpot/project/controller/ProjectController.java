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

import dnd.jackpot.project.service.CommentService;
import dnd.jackpot.project.service.ProjectService;
import dnd.jackpot.user.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectSaveDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
	private final ProjectService service;
	private final CommentService commentService;
	
//	@Secured("ROLE_USER")
	@ApiOperation(value = "게시글 작성")
	@PostMapping("")
	public ProjectDto save(@ApiParam(value = "RequestBody에 json형식") @RequestBody ProjectSaveDto saveDto) {
		return service.save(saveDto);
	}
	
	@ApiOperation(value = "스크랩 처리")
	@PostMapping("/scrap/{projectindex}")
	public ResponseEntity<?> addScrap(@ApiParam(value = "게시글id만 path로 넘겨주시면 됩니다!! user정보는 토큰에서 가져옴") @PathVariable("projectindex") int projectIndex, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		service.addScrap(projectIndex, user.getUserIndex());
		return ResponseEntity.ok("success");
	}
	
	@ApiOperation(value = "게시글 댓글달기")
	@PostMapping("/comment")
	public ResponseEntity<?> Comment(@ApiParam(value = "RequestBody에 json형식으로 코맨트정보만 넘기면됨. user정보는 토큰에서 가져옴") @RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {
		commentService.save(commentDto, user.getUserIndex());
		return ResponseEntity.ok("success");
	}
	
	
////	@Secured("ROLE_USER")
//	@GetMapping("")
//	public PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto){
//		return service.findAll(searchDto);
//	}
	
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
