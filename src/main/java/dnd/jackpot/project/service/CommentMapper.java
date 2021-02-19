package dnd.jackpot.project.service;

import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.entity.Comment;

public class CommentMapper {
	public static CommentDto.getAll map(Comment comment, String date, 
			String authorName, String authorPosition ) {
		
		
		CommentDto.getAll dto = new CommentDto.getAll();
		dto.setId(comment.getCommentId());
		dto.setBody(comment.getBody());
		dto.setDate(date);
		dto.setAuthorName(authorName);
		dto.setAuthorPosition(authorPosition);
		
		return dto;
		
	}
}
