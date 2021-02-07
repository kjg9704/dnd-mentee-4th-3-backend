package dnd.jackpot.project.service;

import dnd.jackpot.project.dto.CommentDto;

public interface CommentService {
	void save(CommentDto commentDto, long userIndex);

}
