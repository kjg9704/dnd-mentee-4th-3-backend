package dnd.jackpot.project.service;

import java.util.List;

import com.google.firebase.messaging.FirebaseMessagingException;

import dnd.jackpot.project.dto.CommentDto;
import dnd.jackpot.project.dto.CommentDto.getAll;
import dnd.jackpot.project.dto.ProjectDto;
import dnd.jackpot.project.entity.Project;
import dnd.jackpot.user.User;

public interface CommentService {

//	List<String> getAllByProject(Project project);

	void save(CommentDto.save commentDto, User user);

	List<CommentDto.getAll> getAllByProject(Project project);
	
	List<ProjectDto> getAllProjectsByUser(User user);

}
