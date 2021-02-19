package dnd.jackpot.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.dto.PagingDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectParticipantRequestDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.dto.ProjectSearchDto;
import dnd.jackpot.project.dto.ProjectStackDto;
import dnd.jackpot.project.entity.Comment;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectMapper;
import dnd.jackpot.project.entity.ProjectParticipant;
import dnd.jackpot.project.entity.ProjectParticipantRequest;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.project.entity.Scrap;
import dnd.jackpot.project.repository.ProjectParticipantRepository;
import dnd.jackpot.project.repository.ProjectParticipantRequestRepository;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.project.repository.ScrapRepository;
import dnd.jackpot.response.Response;
import dnd.jackpot.security.JwtUserDetailsService;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto;
import dnd.jackpot.user.UserDto.simpleResponse;
//import dnd.jackpot.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository repo;
	private final ProjectStackService projectStackService;
	private final ProjectInterestService projectInterestService;
	private final ProjectPositionService projectPositionService;
	private final ScrapRepository scrapRepo;
	private final JwtUserDetailsService userService;
	private final ProjectParticipantRepository projectParticipantRepo;
	private final ProjectParticipantRequestRepository projectPraticipantRequsetRepo;
	private final CommentService commentService;

	@Override
	@Transactional
	public ProjectDto save(ProjectSaveDto saveDto, User user) {
		Project project = ProjectMapper.map(saveDto, user);
		projectStackService.save(saveDto.getStacks(),project);
		projectInterestService.save(saveDto.getInterest(),project);
		projectPositionService.save(saveDto.getPosition(),project);
		project.getParticipant().add(new ProjectParticipant(project, user));
		repo.save(project);
		return toDto(project);
	}

	private ProjectDto toDto(Project project) {
		//		ProjectStack stack = projectStackService.getAllByProject(project);
		List<String> stack = projectStackService.getAllByProject(project);
		List<String> interest = projectInterestService.getAllByProject(project);
		List<String> position = projectPositionService.getAllByProject(project);
		
		List <UserDto.simpleResponse> projectParticipant = new ArrayList<>();
		if(project.isMemberExist()) {
			projectParticipant = getParticipant(project);
		}
		List<CommentDto.getAll> comments = new ArrayList<>(); 
		if(project.isCommentExist()) {
			comments = commentService.getAllByProject(project);
		}		
		LocalDateTime createdDateTime = project.getCreatedAt();
		return ProjectMapper.map(project, createdDateTime, stack, interest, position, comments);
	}
	@Override
	@Transactional(readOnly = true)
	public ProjectDto findById(Long id) {
		Project project = repo.findById(id).orElseThrow();
		return toDto(project);
	}

	@Override
	@Transactional
	public void addScrap(long projectId, User user) {
		Project project = repo.findById(projectId).orElseThrow();
		Scrap scrap = Scrap.builder()
				.project(project)
				.user(user)
				.build();
		scrapRepo.save(scrap);
		project.getScrap().add(scrap);
		repo.save(project);
	}

	@Override
	@Transactional
	public List<simpleResponse> getParticipant(Project project) {
		List<User> participants = projectParticipantRepo.findAllByProject(project);
		List<UserDto.simpleResponse> dtos = new ArrayList<>();
		for(User participant : participants) {
			Long id = participant.getUserIndex();
			String region = participant.getRegion();
			String position = participant.getPosition();
			String emoticon = participant.getEmoticon();
			String career = participant.getCareer();
			UserDto.simpleResponse dto = new UserDto.simpleResponse(id, region, position, career, emoticon);
			dtos.add(dto);
		}
		return dtos;
	}

	
	@Override
	@Transactional
	public void addParticipant(long requestId) {
		ProjectParticipantRequest projectParticipantRequest = projectPraticipantRequsetRepo.findById(requestId).orElseThrow();
		Project project = projectParticipantRequest.getProject();
		User requestUser = userService.loadUserByUserIndex(projectParticipantRequest.getUser().getUserIndex());
		ProjectParticipant projectParticipant = ProjectParticipant.builder()
				.project(project)
				.user(requestUser)
				.build();
		projectParticipantRepo.save(projectParticipant);
		project.getParticipant().add(projectParticipant);
		repo.save(project);
		projectPraticipantRequsetRepo.delete(projectParticipantRequest);
	}
	

	@Override
	@Transactional
	public void participantRequest(long projectId, User user) {
		Project project = repo.findById(projectId).orElseThrow();
		User author = project.getAuthor();
		ProjectParticipantRequest projectParticipantRequest = ProjectParticipantRequest.builder()
				.project(project)
				.user(user)
				.author(author)
				.build();
		projectPraticipantRequsetRepo.save(projectParticipantRequest);
	}

	@Override
	@Transactional
	public ProjectDto delete (Long id) {
		Project project = repo.findById(id).orElseThrow();
		ProjectDto projectDto = new ProjectDto();
		projectDto.setId(project.getId());
		repo.delete(project);
		return projectDto;
	}

	@Override
	@Transactional
	public ProjectDto modify(Long id, ProjectModifyDto modifyDto) {
		Project project = repo.findById(id).orElseThrow();
		//project.update(modifyDto.getTitle(), modifyDto.getShortdesc(),modifyDto.getRegion(),modifyDto.getStack());
		if(Objects.nonNull(modifyDto.getStack())) {
			projectStackService.removeByProject(project);
			projectStackService.save(modifyDto.getStack(), project);
		}
		if(Objects.nonNull(modifyDto.getInterest())) {
			projectInterestService.removeByProject(project);
			projectInterestService.save(modifyDto.getInterest(), project);
		}
		repo.save(project);
		return toDto(project);

	}

	@Override
	public List<ProjectDto> findAllByAuthor(User author) {
		List<Project> pList = repo.findAllByAuthor(author);
		List<ProjectDto> resultList = new ArrayList<>();
		for(Project project : pList) {
			resultList.add(toDto(project));
		}
		return resultList;
	}

	@Override
	public List<ProjectDto> findAllByParticipant(User user) {
		List<ProjectParticipant> pList = projectParticipantRepo.findAllByUser(user);
		List<ProjectDto> resultList = new ArrayList<>();
		for(ProjectParticipant participant : pList) {
			resultList.add(findById(participant.getProject().getId()));
		}
		return resultList;
	}

	@Override
	public List<ProjectParticipantRequestDto> findAllByRequestAuthor(User author) {
		List<ProjectParticipantRequest> requestList = projectPraticipantRequsetRepo.findAllByAuthor(author);
		List<ProjectParticipantRequestDto> resultList = new ArrayList<>();
		for(ProjectParticipantRequest request : requestList) {
			resultList.add(new ProjectParticipantRequestDto(request.getUser().getUserIndex(), request.getProject().getId(), request.getAuthor().getUserIndex()));
		}
		return resultList;
	}

}
