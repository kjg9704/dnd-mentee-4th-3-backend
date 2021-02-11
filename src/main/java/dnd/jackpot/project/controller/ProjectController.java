package dnd.jackpot.project.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
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
import dnd.jackpot.response.BasicResponse;
import dnd.jackpot.response.CommonResponse;
import dnd.jackpot.response.ErrorResponse;
import dnd.jackpot.response.Response;
import dnd.jackpot.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectSaveDto;

@Api(description = "프로젝트 게시글 관련 api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {
	private final ProjectService service;
	private final CommentService commentService;

//	@Secured("ROLE_USER")
	@ApiOperation(value = "게시글 작성")
	@PostMapping("")
	public ResponseEntity<? extends BasicResponse> save(@ApiParam(value = "RequestBody에 json형식") @RequestBody ProjectSaveDto saveDto) {
		try {
			service.save(saveDto);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("게시글 작성 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "스크랩 처리")
	@PostMapping("/scrap/{projectindex}")
	public ResponseEntity<?> addScrap(@ApiParam(value = "게시글id만 path로 넘겨주시면 됩니다!! user정보는 토큰에서 가져옴") @PathVariable("projectindex") int projectIndex, @AuthenticationPrincipal dnd.jackpot.user.User user) {
		try {
			service.addScrap(projectIndex, user.getUserIndex());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("스크랩 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	@ApiOperation(value = "게시글 댓글달기")
	@PostMapping("/comment")
	public ResponseEntity<? extends BasicResponse> Comment(@ApiParam(value = "RequestBody에 json형식으로 코맨트정보만 넘기면됨. user정보는 토큰에서 가져옴") @RequestBody CommentDto commentDto, @AuthenticationPrincipal User user) {
		try {
			commentService.save(commentDto, user.getUserIndex());
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ErrorResponse("댓글 추가 실패", "500"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
	
//////	@Secured("ROLE_USER")
	@GetMapping("")
	public PagingDto<ProjectDto> getAll(ProjectSearchDto searchDto){
		return service.findAll(searchDto);
	}
	
//	@Secured("ROLE_USER")
	@GetMapping("/{id}")
	public ResponseEntity<? extends BasicResponse> getOne(@PathVariable("id") Long id) {
		ProjectDto projectPost;
		try {
			projectPost = service.findById(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 게시글 정보가 없습니다"));
		}
		return ResponseEntity.ok().body(new CommonResponse<ProjectDto>(projectPost));
	}
	
//	@Secured("ROLE_USER")
	@DeleteMapping("/{id}")
	public ResponseEntity<? extends BasicResponse> delete(@PathVariable("id") Long id) {
		try {
			service.delete(id);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 게시글 정보가 없습니다"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
	
//////	@Secured("ROLE_USER")
	@PutMapping("/{id}")
	public ResponseEntity<? extends BasicResponse> modify(@PathVariable("id") Long id,  @RequestBody ProjectModifyDto modifyDto) {
		try {
			service.modify(id, modifyDto);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("일치하는 게시글 정보가 없습니다"));
		}
		return ResponseEntity.ok().body(new Response("success"));
	}
}
