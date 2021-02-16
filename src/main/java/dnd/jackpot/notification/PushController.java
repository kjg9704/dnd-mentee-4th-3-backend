package dnd.jackpot.notification;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.TopicManagementResponse;

import dnd.jackpot.response.BasicResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.FileInputStream;
import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;
@RestController
public class PushController {

    @GetMapping("/test")
    public String sendToToken() throws FirebaseMessagingException {

        // This registration token comes from the client FCM SDKs.
        String registrationToken = "epFTefKQQ82LNPAyVoJnKP:APA91bGrZ_4pjP3ewy6gST8hTrGnLB32fxpZr2925ZUXLX1ZjfGtZxDHAsI-VHDwHB_Yv9l1za2N1sNRTdVUBSowWT38VgsVM1OlQhWrurDjlbvL-Ky2iuZp8Y4B7DXTP9A39CAyuhKX";

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("title", "뜨냐?")
                .putData("content", "뜨냐고씨:발")
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);

        return response;
    }
    
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = { MESSAGING_SCOPE };

    /**
     * HTTP Protocol을 이용한 Push 전송
     * @return
     */
    @GetMapping("/test2")
    public void send() {
        // 1. create message body
        JSONObject jsonValue = new JSONObject();
        jsonValue.put("title", "메시지를 보낼거야2");
        jsonValue.put("content", "너에게로2");

        JSONObject jsonData = new JSONObject();
        jsonData.put("token", "epFTefKQQ82LNPAyVoJnKP:APA91bGrZ_4pjP3ewy6gST8hTrGnLB32fxpZr2925ZUXLX1ZjfGtZxDHAsI-VHDwHB_Yv9l1za2N1sNRTdVUBSowWT38VgsVM1OlQhWrurDjlbvL-Ky2iuZp8Y4B7DXTP9A39CAyuhKX");
        jsonData.put("data", jsonValue);

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("message", jsonData);

        // 2. create token & send push
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + getAccessToken())
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .url("https://fcm.googleapis.com/v1/projects/jackpot-d5f0e/messages:send")
                    .post(RequestBody.create(jsonMessage.toString(), MediaType.parse("application/json")))
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            System.out.println("### response str : " + response.toString());
            System.out.println("### response result : " + response.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostMapping("/addSubscribe/{interest}")
	public void interestSubscribe(@PathVariable("interest") String interest ) throws FirebaseMessagingException{
    		
    	List<String> registrationTokens = Arrays.asList(
    			"epFTefKQQ82LNPAyVoJnKP:APA91bGrZ_4pjP3ewy6gST8hTrGnLB32fxpZr2925ZUXLX1ZjfGtZxDHAsI-VHDwHB_Yv9l1za2N1sNRTdVUBSowWT38VgsVM1OlQhWrurDjlbvL-Ky2iuZp8Y4B7DXTP9A39CAyuhKX"
    		);

    		// Subscribe the devices corresponding to the registration tokens to the
    		// topic.
    		TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
    		    registrationTokens, interest);
    		// See the TopicManagementResponse reference documentation
    		// for the contents of response.
    		System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
	}
    
    @PostMapping("/deleteSubscribe/{interest}")
	public void interestUnSubscribe(@PathVariable("interest") String interest ) throws FirebaseMessagingException{
    	// These registration tokens come from the client FCM SDKs.
    	List<String> registrationTokens = Arrays.asList(
    			"epFTefKQQ82LNPAyVoJnKP:APA91bGrZ_4pjP3ewy6gST8hTrGnLB32fxpZr2925ZUXLX1ZjfGtZxDHAsI-VHDwHB_Yv9l1za2N1sNRTdVUBSowWT38VgsVM1OlQhWrurDjlbvL-Ky2iuZp8Y4B7DXTP9A39CAyuhKX"
    		);

    	// Unsubscribe the devices corresponding to the registration tokens from
    	// the topic.
    	TopicManagementResponse response = FirebaseMessaging.getInstance().unsubscribeFromTopic(
    	    registrationTokens, interest);
    	// See the TopicManagementResponse reference documentation
    	// for the contents of response.
    	System.out.println(response.getSuccessCount() + " tokens were unsubscribed successfully");
    }
    
    @PostMapping("/sendtoSubscribe/{interest}")
	public void sendToSubscribe(@PathVariable("interest") String interest ) throws FirebaseMessagingException{
    	// The topic name can be optionally prefixed with "/topics/".
    	String topic = "IT";

    	// See documentation on defining a message payload.
    	Message message = Message.builder()
    	    .putData("title", "JackPot 알림")
    	    .putData("content", topic + "에 새로운 게시글이 작성되었습니다")
    	    .setTopic(topic)
    	    .build();

    	// Send a message to the devices subscribed to the provided topic.
    	String response = FirebaseMessaging.getInstance().send(message);
    	// Response is a message ID string.
    	System.out.println("Successfully sent message: " + response);
    }
    
    private static String getAccessToken() throws IOException {
        ClassPathResource resource = new ClassPathResource("firebase/jackpot-d5f0e-firebase-adminsdk-46a97-cc8373fa78.json");
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream(resource.getFile()))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }
}
