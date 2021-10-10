package com.vosouq.profile.user.controller;

import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.profile.user.controller.dto.*;
import com.vosouq.profile.user.model.Registration;
import com.vosouq.profile.user.model.User;
import com.vosouq.profile.user.model.UserScore;
import com.vosouq.profile.user.model.UserStatus;
import com.vosouq.profile.user.service.MessageService;
import com.vosouq.profile.user.service.UserService;
import com.vosouq.profile.user.service.feign.ScoringCommunicatorClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;

@VosouqRestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final MessageService messageService;
    private final OnlineUser onlineUser;
    private final ScoringCommunicatorClient scoringCommunicatorClient;

    public UserController(UserService userService,
                          MessageService messageService,
                          OnlineUser onlineUser,
                          ScoringCommunicatorClient scoringCommunicatorClient) {

        this.userService = userService;
        this.messageService = messageService;
        this.onlineUser = onlineUser;
        this.scoringCommunicatorClient = scoringCommunicatorClient;
    }

    @PostMapping("/token/fcm")
    @NoContent
    public void registerFcmToken(@RequestBody @Valid RegisterFcmTokenRequest tokenRequest) {
        messageService.registerFcmToken(onlineUser.getDeviceId(), tokenRequest.getToken());
    }

    @PostMapping("/notification")
    @NoContent
    @ApiIgnore
    public void sendPushNotification(@RequestBody @Valid PushNotificationRequest pushNotificationRequest) {
        messageService.sendPushNotification(
                pushNotificationRequest.getUserId(),
                pushNotificationRequest.getTitle(),
                pushNotificationRequest.getBody(),
                pushNotificationRequest.getSubjectType(),
                pushNotificationRequest.getSubjectId());
    }

    @PostMapping("/register")
    @ApiIgnore
    public RegisterResponse register(@RequestBody @Valid RegisterRequest registerRequest) {

        Registration registration = userService.register(
                registerRequest.getUdid(),
                registerRequest.getDeviceName(),
                registerRequest.getOs(),
                registerRequest.getOsVersion(),
                registerRequest.getAppVersion(),
                registerRequest.getPhoneNumber());

        return new RegisterResponse(
                registration.getDeviceId(),
                registration.getRetryPeriodInSeconds());
    }

    @PostMapping("/verify")
    @ApiIgnore
    public VerifyResponse verify(@RequestBody @Valid VerifyRequest verifyRequest) {
        User user = userService.verify(
                verifyRequest.getDeviceId(),
                verifyRequest.getSmsCode());

        return new VerifyResponse(
                user.getId(),
                user.getPhoneNumber(),
                !user.getStatus().equals(UserStatus.REGISTERED),
                user.getPassword() != null);
    }

    @PostMapping("/password")
    @ApiIgnore
    public CreatePasswordResponse createPassword(@RequestBody @Valid CreatePasswordRequest createPasswordRequest) {
        User user = userService.setPassword(
                createPasswordRequest.getDeviceId(),
                createPasswordRequest.getPassword(),
                createPasswordRequest.getConfirmPassword(),
                createPasswordRequest.getBioToken());

        return new CreatePasswordResponse(
                user.getId(),
                user.getPhoneNumber());
    }

    @PostMapping("/login")
    @ApiIgnore
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {

        User user = userService.login(loginRequest.getDeviceId(), loginRequest.getPassword(), loginRequest.getBioToken());

        return new LoginResponse(
                user.getId(),
                user.getPhoneNumber());
    }

    @PostMapping("/bio-login")
    @ApiIgnore
    public LoginResponse bioLogin(@RequestBody @Valid BioLoginRequest bioLoginRequest) {

        User user = userService.bioLogin(bioLoginRequest.getDeviceId(), bioLoginRequest.getCurrentBioToken(), bioLoginRequest.getNewBioToken());

        return new LoginResponse(
                user.getId(),
                user.getPhoneNumber());
    }

    @GetMapping("/phone/{phoneNumber}")
    public GetByPhoneNumberResponse getByPhoneNumber(@PathVariable @Valid String phoneNumber) {
        User user = userService.getByPhoneNumber(phoneNumber);

        if (user.getId().equals(onlineUser.getUserId()))
            return new GetByPhoneNumberResponse();
        List<UserScore> usersScores = scoringCommunicatorClient.getUsersScores(
                Collections.singletonList(user.getId()), 1, 1);
        return new GetByPhoneNumberResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                "profile/users/" + user.getId() + "/image",
                usersScores.isEmpty() ? -1 : usersScores.get(0).getScore()
        );
    }

    @GetMapping("/{id}")
    public GetByIdResponse getById(@PathVariable @Valid Long id) {
        User user = userService.get(id);
        return new GetByIdResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                "profile/users/" + user.getId() + "/image");
    }

    @GetMapping("/{id}/phoneNumber")
    public GetPhoneNumberByIdResponse getPhoneNumberById(@PathVariable Long id){
        User user = userService.get(id);
        return new GetPhoneNumberByIdResponse(user.getId(),
                user.getPhoneNumber());

    }

    @GetMapping("/device/{deviceId}/info")
    public GetByDeviceIdResponse getByDeviceId(@PathVariable @Valid Long deviceId) {
        User user = userService.getByDeviceId(deviceId);
        return new GetByDeviceIdResponse(
                user.getId(),
                user.getPhoneNumber(),
                user.getNationalCode());
    }

    @GetMapping("/self")
    public GetCurrentUserResponse getCurrentUser() {
        User user = userService.get(onlineUser.getUserId());
        return new GetCurrentUserResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                "profile/users/" + user.getId() + "/image");
    }

    @PutMapping("/kyc/complete")
    @ApiIgnore
    public UserResponse completeKycInfo(@RequestBody CompleteKycInfoRequest completeKycInfoRequest) {
        User user = userService.completeKycStatus(onlineUser.getUserId(), completeKycInfoRequest.getProfileImageAddress());
        return new UserResponse(
                user.getPhoneNumber(),
                user.getNationalCode(),
                user.getSerial(),
                user.getBirthDate(),
                user.getFirstName(),
                user.getLastName(),
                user.getProfileImageAddress());
    }

    @PostMapping("/kyc")
    @NoContent
    @ApiIgnore
    public void addKycInfo(@RequestBody AddKycInfoRequest addKycInfoRequest) {
        userService.addKycInfo(
                onlineUser.getUserId(),
                addKycInfoRequest.getNationalCode(),
                addKycInfoRequest.getSerial(),
                addKycInfoRequest.getBirthDate(),
                addKycInfoRequest.getFirstName(),
                addKycInfoRequest.getLastName());
    }

    @GetMapping("/image")
    @ApiIgnore
    public ResponseEntity<Resource> downloadProfileImage() {

        User user = userService.get(onlineUser.getUserId());
        return getImage(user.getProfileImageAddress());
    }

    @GetMapping("/{id}/image")
    @ApiIgnore
    public ResponseEntity<Resource> downloadProfileImage(@PathVariable @Valid Long id) {

        User user = userService.get(id);
        return getImage(user.getProfileImageAddress());
    }

    private ResponseEntity<Resource> getImage(String profileImageAddress) {
        MediaType mediaType = MediaType.IMAGE_JPEG;

        File file = new File(profileImageAddress);
        InputStreamResource resource = null;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            log.error("", e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(mediaType)
                .body(resource);
    }

}
