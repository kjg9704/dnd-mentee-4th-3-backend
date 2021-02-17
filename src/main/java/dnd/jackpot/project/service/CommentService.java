package dnd.jackpot.project.service;

import com.google.firebase.messaging.FirebaseMessagingException;

import dnd.jackpot.project.dto.CommentDto;

public interface CommentService {
	void save(CommentDto commentDto, long userIndex);
	void sendPush(String registrationToken) throws FirebaseMessagingException;

}
