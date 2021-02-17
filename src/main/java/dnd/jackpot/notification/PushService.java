package dnd.jackpot.notification;

import com.google.firebase.messaging.FirebaseMessagingException;

public interface PushService {

	public String sendToToken(String registrationToken) throws FirebaseMessagingException;
}
