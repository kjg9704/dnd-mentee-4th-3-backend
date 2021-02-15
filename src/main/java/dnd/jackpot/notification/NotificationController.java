//package dnd.jackpot.notification;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Arrays;
//
//import org.json.JSONObject;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.MediaType;
//
//
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//
//import io.swagger.annotations.Api;
//import lombok.RequiredArgsConstructor;
//
//@Api(description = "알림기능")
//@RestController
//@RequiredArgsConstructor
//public class NotificationController {
//	
//	private FirebaseCloudMessageService service; 
//	
//	@GetMapping("/test")
//	public void test() throws IOException {
//		//service.sendMessageTo("epFTefKQQ82LNPAyVoJnKP:APA91bGrZ_4pjP3ewy6gST8hTrGnLB32fxpZr2925ZUXLX1ZjfGtZxDHAsI-VHDwHB_Yv9l1za2N1sNRTdVUBSowWT38VgsVM1OlQhWrurDjlbvL-Ky2iuZp8Y4B7DXTP9A39CAyuhKX", "test", "test!");
//try {    
//            
//            String path = "C:\\Users\\KIM\\Desktop\\SpringBoot\\dnd-mentee-4th-3-backend\\src\\main\\resources\\firebase\\jackpot-d5f0e-firebase-adminsdk-46a97-cc8373fa78.json";           
//            String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
//            String[] SCOPES = { MESSAGING_SCOPE };
//            
//            GoogleCredential googleCredential = GoogleCredential
//                                .fromStream(new FileInputStream(path))
//                                .createScoped(Arrays.asList(SCOPES));
//            googleCredential.refreshToken();
//                                
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("content-type" , MediaType.APPLICATION_JSON_VALUE);
//            headers.add("Authorization", "Bearer " + googleCredential.getAccessToken());
//            System.out.println(headers);
//            JSONObject notification = new JSONObject();
//            notification.put("body", "TEST");
//            notification.put("title", "TEST");
//            System.out.println(notification);
//            JSONObject message = new JSONObject();
//            message.put("token", "epFTefKQQ82LNPAyVoJnKP:APA91bGrZ_4pjP3ewy6gST8hTrGnLB32fxpZr2925ZUXLX1ZjfGtZxDHAsI-VHDwHB_Yv9l1za2N1sNRTdVUBSowWT38VgsVM1OlQhWrurDjlbvL-Ky2iuZp8Y4B7DXTP9A39CAyuhKX");
//            message.put("notification", notification);
//            System.out.println(message);
//            JSONObject jsonParams = new JSONObject();
//            jsonParams.put("message", message);
//            System.out.println(jsonParams);
//            HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(jsonParams, headers);
//            RestTemplate rt = new RestTemplate();            
//            
//            ResponseEntity<String> res = rt.exchange("https://fcm.googleapis.com/v1/projects/jackpot-d5f0e/messages:send"
//                    , HttpMethod.POST
//                    , httpEntity
//                    , String.class);
//            if (res.getStatusCode() != HttpStatus.OK) {
//            	System.out.println("FCM-Exception");
//            	System.out.println(res.getStatusCode().toString());
//            	System.out.println(res.getHeaders().toString());
//            	System.out.println(res.getBody().toString());
//                
//            } else {
//            	System.out.println(res.getStatusCode().toString());
//            	System.out.println(res.getHeaders().toString());
//            	System.out.println(res.getBody().toLowerCase());     
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
