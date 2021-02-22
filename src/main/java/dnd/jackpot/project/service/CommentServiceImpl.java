package dnd.jackpot.project.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.messaging.FirebaseMessagingException;

import dnd.jackpot.notification.PushService;
import dnd.jackpot.project.dao.ProjectDao;
import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.dto.CommentDto.getAll;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.entity.Comment;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectMapper;
import dnd.jackpot.project.entity.ProjectParticipant;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.project.repository.CommentRepository;
import dnd.jackpot.project.repository.ProjectRepository;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserDto.simpleResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepo;
	private final PushService pushService;
	private final ProjectDao projectDao;
	private final ProjectRepository projectRepo;
	private final ProjectStackService projectStackService;
	private final ProjectPositionService projectPositionService;
	
	@Override
	public void save(CommentDto.save commentDto, User author) {
		LocalDate date = LocalDate.now();
		Project project = projectDao.findById(commentDto.getProjectId());
		commentRepo.save(Comment.builder()
				.body(commentDto.getBody())
				.privacy(commentDto.isPrivacy())
				.date(date.toString())
				.project(project)
				.user(author)
				.build());
	}
	public void delete(long id) {
		commentRepo.deleteById(id);
	}
	
	@Override 
	@Transactional(readOnly=true)
	public List<CommentDto.getAll> getAllByProject(Project project){
		List<Comment> comments = commentRepo.findAllByProject(project);
		List<CommentDto.getAll> dtos = new ArrayList<>();
		for(Comment comment : comments) {
			dtos.add(toDto(comment));
		}
		return dtos;
	}
	
	private CommentDto.getAll toDto(Comment comment) {
		String date = comment.getDate();
		String authorName = comment.getUser().getName();
		String authorPosition = comment.getUser().getPosition();
		return CommentMapper.map(comment, date, authorName, authorPosition );
	}

	@Override
	@Transactional
	public List<ProjectDto> getAllProjectsByUser(User user) {
		List<Comment> commentList = commentRepo.findAllByUser(user);
		List<ProjectDto> resultList = new ArrayList<>();
		for(Comment com : commentList) {
			Project project = projectRepo.findById(com.getProject().getId()).orElseThrow();
			List<String> stack = projectStackService.getAllByProject(project);
			List<String> position = projectPositionService.getAllByProject(project);
			List<getAll> comments = new ArrayList<>();
			List<simpleResponse> participants = new ArrayList<>();
			List<simpleResponse> requests = new ArrayList<>();
			for(Comment comm : project.getComment()) {
				comments.add(new getAll(comm.getCommentId(), comm.getBody(), comm.getDate(), comm.isPrivacy(), comm.getUser().getEmoticon(), comm.getUser().getName(), comm.getUser().getPosition()));
			}
			for(ProjectParticipant users : project.getParticipant()) {
				participants.add(new simpleResponse(users.getUser().getUserIndex(), users.getUser().getName(), users.getUser().getRegion(), users.getUser().getPosition(), users.getUser().getCareer(), users.getUser().getEmoticon()));
			}
			resultList.add(ProjectMapper.map(project, project.getCreatedAt(), stack, position, comments, participants, requests));
		}
		return resultList;
	}
}
