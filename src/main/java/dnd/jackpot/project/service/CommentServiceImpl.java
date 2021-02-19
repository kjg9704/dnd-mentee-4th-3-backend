package dnd.jackpot.project.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessagingException;

import dnd.jackpot.notification.PushService;
import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.entity.Comment;
import dnd.jackpot.project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepo;
	private final PushService pushService;
	@Override
	public void save(CommentDto commentDto, long userIndex) {
		LocalDate date = LocalDate.now();
		commentRepo.save(Comment.builder()
				.body(commentDto.getBody())
				.privacy(commentDto.isPrivacy())
				.date(date.toString())
				.projectId(commentDto.getProjectId())
				.userIndex(userIndex)
				.build());
	}
	@Override
	public void sendPush(String registrationToken) throws FirebaseMessagingException {
		pushService.sendToToken(registrationToken);
	}
	
	

}
