package com.vosouq.profile.user.service.fcm;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FCMService {

    private static final String TOPIC = "vosouq-develop";

    public void sendMessage(Map<String, String> data,
                            String title,
                            String body,
                            String token) throws InterruptedException, ExecutionException {

        Message message = getPreconfiguredMessageWithData(data, title, body, token);
        String response = sendAndGetResponse(message);
        log.info("Sent message with data. Topic: {}, response: {}", TOPIC, response);
    }

    private String sendAndGetResponse(Message message) throws InterruptedException, ExecutionException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }

    private AndroidConfig getAndroidConfig() {
        return AndroidConfig
                .builder()
                .setTtl(Duration.ofMinutes(2).toMillis())
                .setCollapseKey(TOPIC)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(
                        AndroidNotification
                                .builder()
                                .setTag(TOPIC)
                                .build())
                .build();
    }

    private ApnsConfig getApnsConfig() {
        return ApnsConfig
                .builder()
                .setAps(Aps
                        .builder()
                        .setCategory(TOPIC)
                        .setThreadId(TOPIC)
                        .build())
                .build();
    }

    private Message getPreconfiguredMessageWithData(Map<String, String> data,
                                                    String title,
                                                    String body,
                                                    String token) {

        return getPreconfiguredMessageBuilder(title, body)
                .putAllData(data)
                .setToken(token)
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(String title, String body) {
        AndroidConfig androidConfig = getAndroidConfig();
        ApnsConfig apnsConfig = getApnsConfig();
        return Message
                .builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .setNotification(new Notification(title, body));
    }

}
