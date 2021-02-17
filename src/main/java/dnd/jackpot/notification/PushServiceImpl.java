package dnd.jackpot.notification;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import dnd.jackpot.project.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PushServiceImpl implements PushService {

	@Override
	public String sendToToken(String registrationToken) throws FirebaseMessagingException {
		Message message = Message.builder()
                .putData("title", "게시글 댓글 알림")
                .putData("content", "회원님의 게시글에 댓글이 작성되었습니다")
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);

        return response;
	}
	
}
