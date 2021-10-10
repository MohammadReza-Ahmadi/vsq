package com.vosouq.profile.user.service;

import java.util.Locale;

public interface MessageService {

    void registerFcmToken(Long deviceId, String token);

    void sendPushNotification(Long userId,
                              String title,
                              String body,
                              String subjectType,
                              Long subjectId);

    void sendSms(String phoneNumber, String content, Locale locale);

}
