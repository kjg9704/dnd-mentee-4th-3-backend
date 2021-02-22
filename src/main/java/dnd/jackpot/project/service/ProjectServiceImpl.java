package dnd.jackpot.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.dto.ProjectModifyDto;
import dnd.jackpot.project.dto.ProjectParticipantRequestDto;
import dnd.jackpot.project.dto.ProjectSaveDto;
import dnd.jackpot.project.entity.ERegion;
import dnd.jackpot.project.entity.EprojectStatus;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectMapper;
import dnd.jackpot.project.entity.ProjectParticipant;
import dnd.jackpot.project.entity.ProjectParticipantRequest;
import dnd.jackpot.project.entity.ProjectScrap;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.project.repository.ProjectParticipantRepository;
import dnd.jackpot.project.repository.ProjectParticipantRequestRepository;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.project.repository.ProjectScrapRepository;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto;
import dnd.jackpot.user.UserDto.simpleResponse;
import dnd.jackpot.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

	private final ProjectRepository repo;
	private final ProjectStackService projectStackService;
	private final ProjectPositionService projectPositionService;
	private final ProjectScrapRepository scrapRepo;
	private final UserRepository userRepo;
	private final ProjectParticipantRepository projectParticipantRepo;
	private final ProjectParticipantRequestRepository projectPraticipantRequsetRepo;
	private final CommentService commentService;
	
	@Override
	@Transactional
	public ProjectDto save(ProjectSaveDto saveDto, User user) {
		Project project = ProjectMapper.map(saveDto, user);
		projectStackService.save(saveDto.getStacks(),project);
		projectPositionService.save(saveDto.getPosition(),project);
		EprojectStatus status = EprojectStatus.valueOf("모집중");
		project.setStatus(status);
		project.getParticipant().add(new ProjectParticipant(project, user));
		repo.save(project);
		return toDto(project);
	}

	@Transactional
	private ProjectDto toDto(Project project) {
		List<String> stack = projectStackService.getAllByProject(project);
		List<String> position = projectPositionService.getAllByProject(project);

		
		List <UserDto.simpleResponse> projectParticipant = new ArrayList<>();
		if(project.isMemberExist()) {
			projectParticipant = getParticipant(project);
		}
		List<CommentDto.getAll> comments = new ArrayList<>(); 
		if(project.isCommentExist()) {
			comments = commentService.getAllByProject(project);
		}
		List<UserDto.simpleResponse> projectRequest = new ArrayList<>();
		if(!project.getRequest().isEmpty()) {
			projectRequest = getRequest(project);
		}
		System.out.println("------테스트111");
		for(simpleResponse index : projectParticipant) {
			System.out.println(index.getName());
		}
		
		System.out.println("------테스트끝111");
		LocalDateTime createdDateTime = project.getCreatedAt();
		return ProjectMapper.map(project, createdDateTime, stack, position, comments, projectParticipant,projectRequest);
	}

	
	@Override
	@Transactional(readOnly = true)
	public ProjectDto findById(Long id) {
		Project project = repo.findById(id).orElseThrow();
		return toDto(project);
	}
	
	@Override
	@Transactional
	public void changeStatus(long projectId, String status) {
		Project project = repo.findById(projectId).orElseThrow();
		EprojectStatus changeStatus = EprojectStatus.valueOf(status);
		project.setStatus(changeStatus);
	}

	@Override
	@Transactional
	public void addScrap(long projectId, User user) {
		Project project = repo.findById(projectId).orElseThrow();
		ProjectScrap scrap = ProjectScrap.builder()
				.project(project)
				.user(user)
				.build();
		scrapRepo.save(scrap);
		project.getScrap().add(scrap);
		repo.save(project);
	}
	
	
	@Override
	@Transactional
	public List<simpleResponse> getRequest (Project project) {
		List<ProjectParticipantRequest> requests = projectPraticipantRequsetRepo.findAllByProject(project);
		List<UserDto.simpleResponse> dtos = new ArrayList<>();
		
		for(ProjectParticipantRequest request : requests) {
			User Pparticipant = request.getUser();
			Long id = Pparticipant.getUserIndex();
			ERegion region = Pparticipant.getRegion();
			String position = Pparticipant.getPosition();
			String emoticon = Pparticipant.getEmoticon();
			String career = Pparticipant.getCareer();
			UserDto.simpleResponse dto = new UserDto.simpleResponse(id, Pparticipant.getName(), region, position, career, emoticon);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	@Transactional
	public List<simpleResponse> getParticipant(Project project) {
		List<ProjectParticipant> participants = projectParticipantRepo.findAllByProject(project);
		List<UserDto.simpleResponse> dtos = new ArrayList<>();
		
		for(ProjectParticipant participant : participants) {
			User Pparticipant = participant.getUser();
			Long id = Pparticipant.getUserIndex();
			ERegion region = Pparticipant.getRegion();
			String position = Pparticipant.getPosition();
			String emoticon = Pparticipant.getEmoticon();
			String career = Pparticipant.getCareer();
			UserDto.simpleResponse dto = new UserDto.simpleResponse(id, Pparticipant.getName(), region, position, career, emoticon);
			dtos.add(dto);
		}
		System.out.println("------테스트");
		for(simpleResponse index : dtos) {
			System.out.println(index.getName());
		}
		
		System.out.println("------테스트끝");
		return dtos;
	}

	
	@Override
	@Transactional
	public void addParticipant(long requestId) {
		ProjectParticipantRequest projectParticipantRequest = projectPraticipantRequsetRepo.findById(requestId).orElseThrow();
		Project project = projectParticipantRequest.getProject();
		User requestUser = userRepo.findById(projectParticipantRequest.getUser().getUserIndex()).orElseThrow();
		project.setMemberExist(true);
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
		project.update(modifyDto.getTitle(), modifyDto.getShortdesc(),modifyDto.getRegion(),modifyDto.getOnline(), modifyDto.getDuration(), modifyDto.getInterest());
		if(Objects.nonNull(modifyDto.getStack())) {
			projectStackService.removeByProject(project);
			projectStackService.save(modifyDto.getStack(), project);
		}
		if(Objects.nonNull(modifyDto.getPosition())) {
			projectPositionService.removeByProject(project);
			projectPositionService.save(modifyDto.getPosition(), project);
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
