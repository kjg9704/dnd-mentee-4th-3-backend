package dnd.jackpot.notification;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initFirebase(){
        try {
 //       	String path = new ClassPathResource("/firebase").getFile().getAbsolutePath();
//        	System.out.println(path);
 //       	File file = new ClassPathResource("/firebase/jackpot-1611239774705-firebase-adminsdk-xlp80-fa2c872b91.json").getFile();
            FileInputStream serviceAccount = new FileInputStream(new File("/home/ec2-user/app/firstTest/dnd-mentee-4th-3-backend/src/main/resources/firebase/jackpot-1611239774705-firebase-adminsdk-xlp80-fa2c872b91.json"));
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
  //                  .setDatabaseUrl("https://{사용자마다 다름}.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
