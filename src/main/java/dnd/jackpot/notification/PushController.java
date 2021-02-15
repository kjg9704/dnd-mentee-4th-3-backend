package dnd.jackpot.notification;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
