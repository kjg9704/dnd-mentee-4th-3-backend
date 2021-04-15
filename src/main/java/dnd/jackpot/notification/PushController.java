package dnd.jackpot.notification;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.TopicManagementResponse;

import dnd.jackpot.project.entity.Einterest;
import dnd.jackpot.user.User;
import dnd.jackpot.user.UserRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.FileInputStream;
import java.io.IOException;

import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.List;
@RestController
@RequiredArgsConstructor
public class PushController {
	
	private final UserRepository userRepo;
	private final InterestSubscribeRepository interestSubscribeRepo;
	private final PushService pushService;

    @GetMapping("/test")
    public String sendToToken() throws FirebaseMessagingException {

        // This registration token comes from the client FCM SDKs.
        String registrationToken = "eFziRRR3ReiBW1uKsLL1J9:APA91bFiESXEIWHkWxc353JVf8FLLta_UkPQ-_tOGi3cnsMKyFEMUXcqiOoYDpVSiWLN9LM4oL1oRzr1j5_NZiXiS5ftUJrHtBHQ-EKJycfs_px4irogbLAdH5s_42uSTLQBl3GJ0xOy";

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .putData("title", "경창님")
                .putData("content", "뜨시나요??")
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
        jsonValue.put("title", "경창님");
        jsonValue.put("content", "뜨시나요??");

        JSONObject jsonData = new JSONObject();
        jsonData.put("token", "eFziRRR3ReiBW1uKsLL1J9:APA91bFiESXEIWHkWxc353JVf8FLLta_UkPQ-_tOGi3cnsMKyFEMUXcqiOoYDpVSiWLN9LM4oL1oRzr1j5_NZiXiS5ftUJrHtBHQ-EKJycfs_px4irogbLAdH5s_42uSTLQBl3GJ0xOy");
        jsonData.put("data", jsonValue);

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("message", jsonData);

        // 2. create token & send push
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + getAccessToken())
                    .addHeader("Content-Type", "application/json; UTF-8")
                    .url("https://fcm.googleapis.com/v1/projects/jackpot-1611239774705/messages:send")
                    .post(RequestBody.create(jsonMessage.toString(), MediaType.parse("application/json")))
                    .build();
            Response response = okHttpClient.newCall(request).execute();

            System.out.println("### response str : " + response.toString());
            System.out.println("### response result : " + response.isSuccessful());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @ApiOperation(value = "관심사 알림 추가")
    @Transactional
    @PostMapping("/addSubscribe/{interest}")
	public void interestSubscribe(@PathVariable("interest") String interest, @AuthenticationPrincipal User user) throws FirebaseMessagingException{
    	String Topic = pushService.interestMapper(interest);
    	List<String> registrationTokens = Arrays.asList(
    			user.getRegistrationToken()
    		);

    		TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(
    		    registrationTokens, Topic);
    		Einterest inter = Einterest.valueOf(interest);
    		User persistenceUser = userRepo.save(user);
    		persistenceUser.getSubscribes().add(InterestSubscribe.builder()
    				.user(user)
    				.interest(inter)
    				.build());
    		
    		System.out.println(response.getSuccessCount() + " tokens were subscribed successfully");
	}
    
    @ApiOperation(value = "관심사 알림 삭제")
    @Transactional
    @DeleteMapping("/deleteSubscribe/{interest}")
	public void interestUnSubscribe(@PathVariable("interest") String interest, @AuthenticationPrincipal User user ) throws FirebaseMessagingException{
    	String Topic = pushService.interestMapper(interest);
    	List<String> registrationTokens = Arrays.asList(
    			user.getRegistrationToken()
    		);
    	TopicManagementResponse response = FirebaseMessaging.getInstance().unsubscribeFromTopic(
    	    registrationTokens, Topic);
    	Einterest inter = Einterest.valueOf(interest);
    	User persistenceUser = userRepo.save(user);
    	InterestSubscribe interSub = interestSubscribeRepo.findByInterestAndUser(inter, user).orElseThrow();
    	persistenceUser.getSubscribes().remove(interSub);
    	System.out.println(response.getSuccessCount() + " tokens were unsubscribed successfully");
    }
    
    
    private static String getAccessToken() throws IOException {
        ClassPathResource resource = new ClassPathResource("firebase/jackpot-1611239774705-firebase-adminsdk-xlp80-fa2c872b91.json");
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream(resource.getFile()))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }
}
