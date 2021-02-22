package dnd.jackpot.project.service;

import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.entity.Comment;

public class CommentMapper {
	public static CommentDto.getAll map(Comment comment, String date, boolean privacy, String emoticon,
			String authorName, String authorPosition ) {
		
		
		CommentDto.getAll dto = new CommentDto.getAll();
		dto.setId(comment.getCommentId());
		dto.setBody(comment.getBody());
		dto.setDate(date);
		dto.setPrivacy(privacy);
		dto.setEmoticon(emoticon);
		dto.setAuthorName(authorName);
		dto.setAuthorPosition(authorPosition);
		
		return dto;
		
	}
}
