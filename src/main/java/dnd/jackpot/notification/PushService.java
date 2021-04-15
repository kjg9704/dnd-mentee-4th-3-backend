package dnd.jackpot.notification;

import com.google.firebase.messaging.FirebaseMessagingException;


public interface PushService {

	public String sendCommentToToken(String registrationToken) throws FirebaseMessagingException;
	
	public String sendInterestSubscribe(String interest) throws FirebaseMessagingException;
	
	public String sendParticipantRequestToToken(String registrationToken) throws FirebaseMessagingException;
	
	public String sendParticipantAcceptToToken(String registrationToken) throws FirebaseMessagingException;
	
	public String interestMapper(String interest);
}
