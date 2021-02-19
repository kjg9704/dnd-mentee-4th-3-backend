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
	public String sendCommentToToken(String registrationToken) throws FirebaseMessagingException {
		Message message = Message.builder()
                .putData("title", "게시글 댓글 알림")
                .putData("content", "회원님의 게시글에 댓글이 작성되었습니다")
                .setToken(registrationToken)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);

        return response;
	}

	@Override
	public String sendInterestSubscribe(String interest) throws FirebaseMessagingException {

    	Message message = Message.builder()
    		.putData("title", "관심분야 게시글 알림")
            .putData("content", "관심분야의 새로운 게시글이 등록되었습니다")
    	    .setTopic(interest)
    	    .build();

    	String response = FirebaseMessaging.getInstance().send(message);
    	System.out.println("Successfully sent message: " + response);
    	return response;
	}

	@Override
	public String sendParticipantRequestToToken(String registrationToken) throws FirebaseMessagingException {
		Message message = Message.builder()
                .putData("title", "프로젝트 참가 신청")
                .putData("content", "새로운 프로젝트 참가 신청이 있습니다")
                .setToken(registrationToken)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);

        return response;
	}

	@Override
	public String sendParticipantAcceptToToken(String registrationToken) throws FirebaseMessagingException {
		Message message = Message.builder()
                .putData("title", "프로젝트 참가 수락")
                .putData("content", "프로젝트 참가 요청이 수락되었습니다")
                .setToken(registrationToken)
                .build();

        String response = FirebaseMessaging.getInstance().send(message);
        System.out.println("Successfully sent message: " + response);

        return response;
	}
	
}
