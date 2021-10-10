package com.vosouq.profile.user.service.fcm;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.vosouq.profile.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
@Slf4j
public class FCMInitializer {

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    private final UserService userService;

    public FCMInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options =
                    FirebaseOptions
                            .builder()
                            .setCredentials(
                                    GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream()))
                            .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
