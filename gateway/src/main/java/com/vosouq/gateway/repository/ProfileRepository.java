package com.vosouq.gateway.repository;

import com.vosouq.gateway.model.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile")
public interface ProfileRepository {

    @PostMapping("/users/register")
    RegisterUserResponse register(@RequestBody RegisterUserRequest registerUserRequest);

    @PostMapping("/users/verify")
    VerifyUserResponse verify(@RequestBody VerifyUserRequest verifyUserRequest);

    @PostMapping("/users/password")
    LoginUserResponse createPassword(@RequestBody CreatePasswordRequest createPasswordRequest);

    @PostMapping("/users/login")
    LoginUserResponse login(@RequestBody LoginUserRequest loginUserRequest);

    @PostMapping("/users/bio-login")
    LoginUserResponse bioLogin(@RequestBody BioLoginUserRequest bioLoginUserRequest);

    @GetMapping("/users/device/{deviceId}/info")
    UserInfoResponse getUserInfoByDeviceId(@PathVariable Long deviceId);

}
