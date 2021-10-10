package com.vosouq.profile.user.service.impl;

import com.vosouq.commons.util.MessageUtil;
import com.vosouq.profile.user.model.Device;
import com.vosouq.profile.user.service.DeviceService;
import com.vosouq.profile.user.service.MessageService;
import com.vosouq.profile.user.service.fcm.FCMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final DeviceService deviceService;
    private final FCMService fcmService;

    public MessageServiceImpl(DeviceService deviceService,
                              FCMService fcmService) {

        this.deviceService = deviceService;
        this.fcmService = fcmService;
    }

    @Override
    @Async
    public void registerFcmToken(Long deviceId, String token) {
        log.info("registering fcm token: {} for deviceId: {}", token, deviceId);
        deviceService.registerFcmToken(deviceId, token);
    }

    @Override
    @Async
    public void sendPushNotification(Long userId,
                                     String title,
                                     String body,
                                     String subjectType,
                                     Long subjectId) {

        log.info("sending notification for all devices of userId: {}", userId);
        log.info("the content to be pushed is: [title:{}, body:{}, subjectId:{}, subjectType:{}]", title, body, subjectId, subjectType);

        List<Device> devices = deviceService.getAll(userId);

        devices.forEach(device -> {

            String fcmToken = device.getFcmToken();

            if (!StringUtils.isEmpty(fcmToken)) {

                log.info("sending notification for deviceId:{} with token:{}", device.getId(), fcmToken);

                try {
                    Map<String, String> data = new HashMap<>();
                    data.put("subjectType", subjectType);
                    data.put("subjectId", String.valueOf(subjectId));

                    fcmService.sendMessage(data, title, body, fcmToken);
                } catch (Throwable throwable) {
                    log.error("send push notification error", throwable);
                }
            }
        });
    }

    @Override
    @Async
    public void sendSms(String phoneNumber, String smsContent, Locale locale) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> response = restTemplate.exchange(
                "https://api.kavenegar.com/v1/6E32646B38344C664A4F4752502B6145466E5047327A666A67353935762F66695A696437355237384F51593D/sms/send.json" +
                        "?" +
                        "receptor={phoneNumber}" +
                        "&" +
                        "message=" + MessageUtil.getMessage("OTP_SMS_CONTENT", locale, smsContent),
                HttpMethod.POST,
                entity,
                String.class,
                phoneNumber);
    }
}
