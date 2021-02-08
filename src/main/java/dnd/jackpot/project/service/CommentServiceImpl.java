package dnd.jackpot.project.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.entity.Comment;
import dnd.jackpot.project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepo;
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

}
