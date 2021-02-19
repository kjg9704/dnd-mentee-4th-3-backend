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
import dnd.jackpot.project.entity.Comment;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.project.entity.ProjectStack;
import dnd.jackpot.project.repository.CommentRepository;
import dnd.jackpot.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepo;
	private final PushService pushService;
	private final ProjectDao projectDao;
	
	@Override
	public void save(CommentDto.save commentDto, User author) {
		LocalDate date = LocalDate.now();
		Project project = projectDao.findById(commentDto.getProjectId());
		project.setCommentExist(true);
		commentRepo.save(Comment.builder()
				.body(commentDto.getBody())
				.privacy(commentDto.isPrivacy())
				.date(date.toString())
				.project(project)
				.user(author)
				.build());
	}
	@Override
	public void sendPush(String registrationToken) throws FirebaseMessagingException {
		pushService.sendToToken(registrationToken);
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
		String body = comment.getBody();
		String authorName = comment.getUser().getName();
		String authorPosition = comment.getUser().getJob();
		return CommentMapper.map(comment, date, authorName, authorPosition );
	}
	

}
