package com.vosouq.contract.service.feign;

import com.vosouq.contract.model.PushNotificationRequest;
import com.vosouq.contract.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile")
public interface ProfileServiceClient {

    @GetMapping("/users/{id}")
    User findById(@PathVariable Long id);

    @PostMapping("/users/notification")
    void sendPush(@RequestBody PushNotificationRequest pushNotificationRequest);
}
