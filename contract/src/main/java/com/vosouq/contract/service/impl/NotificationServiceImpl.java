package com.vosouq.contract.service.impl;

import com.vosouq.contract.model.PushNotificationRequest;
import com.vosouq.contract.service.NotificationService;
import com.vosouq.contract.service.feign.ProfileServiceClient;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final ProfileServiceClient profileServiceClient;

    public NotificationServiceImpl(ProfileServiceClient profileServiceClient) {
        this.profileServiceClient = profileServiceClient;
    }

    @Override
    public void send(Long userId, String body, Long subjectId, String subjectType) {
        PushNotificationRequest request = new PushNotificationRequest(
                userId,
                "وثوق",
                body,
                subjectId,
                subjectType);

        profileServiceClient.sendPush(request);
    }
}
