package com.vosouq.gateway.controller;

import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.gateway.controller.dto.CreatePasswordRequest;
import com.vosouq.gateway.controller.dto.VerifyUserKycResponse;
import com.vosouq.gateway.controller.dto.*;
import com.vosouq.gateway.model.*;
import com.vosouq.gateway.service.ProfileService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@VosouqRestController
@RequestMapping("/auth")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", value = "client identification", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = "client_secret", value = "client secret key", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public RegistrationResponse register(@RequestBody @Valid RegistrationRequest registrationRequest) {

        RegisterUserResponse registerUserResponse = profileService.register(
                registrationRequest.getUdid(),
                registrationRequest.getDeviceName(),
                registrationRequest.getOs(),
                registrationRequest.getOsVersion(),
                registrationRequest.getAppVersion(),
                registrationRequest.getPhoneNumber());

        return new RegistrationResponse(
                registerUserResponse.getDeviceId(),
                registerUserResponse.getRetryPeriodInSeconds());
    }

    @PostMapping("/verify")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", value = "client identification", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = "client_secret", value = "client secret key", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public VerificationResponse verify(@RequestBody @Valid VerificationRequest verificationRequest) {

        Verify verify = profileService.verify(
                verificationRequest.getDeviceId(),
                verificationRequest.getSmsCode());

        return new VerificationResponse(
                verify.getToken(),
                verify.getTokenType(),
                verify.isKycCompleted(),
                verify.isPasswordDefined());
    }

    @PostMapping("/password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", value = "client identification", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = "client_secret", value = "client secret key", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public LoginResponse createPassword(@RequestBody @Valid CreatePasswordRequest createPasswordRequest) {

        Login login = profileService.createPassword(
                createPasswordRequest.getDeviceId(),
                createPasswordRequest.getPassword(),
                createPasswordRequest.getConfirmPassword());

        return new LoginResponse(login.getToken(), login.getBioToken());
    }

    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", value = "client identification", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = "client_secret", value = "client secret key", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {

        Login login = profileService.login(loginRequest.getDeviceId(), loginRequest.getPassword());

        return new LoginResponse(login.getToken(), login.getBioToken());
    }

    @PostMapping("/bio-login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_id", value = "client identification", required = true, paramType = "header", dataTypeClass = String.class),
            @ApiImplicitParam(name = "client_secret", value = "client secret key", required = true, paramType = "header", dataTypeClass = String.class)
    })
    public LoginResponse bioLogin(@RequestBody @Valid BioLoginRequest bioLoginRequest) {

        Login login = profileService.bioLogin(bioLoginRequest.getDeviceId(), bioLoginRequest.getBioToken());

        return new LoginResponse(login.getToken(), login.getBioToken());
    }

    @PostMapping("/kyc/verify-user")
    public VerifyUserKycResponse verifyUserKyc(
            @RequestParam(name = "method") String method,
            @RequestParam(name = "deviceId") Long deviceId,
            @RequestParam MultipartFile video) {

        VerifyUserKyc verifyUserKyc = profileService.verifyUserKyc(deviceId, method, video);

        return new VerifyUserKycResponse(
                verifyUserKyc.getToken(),
                verifyUserKyc.getTokenType());

    }

    @PostMapping("/kyc/spoof-check")
    @NoContent
    public void userSpoofCheck(@RequestParam(name = "deviceId") Long deviceId,
                                             @RequestParam MultipartFile image01,
                                             @RequestParam MultipartFile image02,
                                             @RequestParam MultipartFile image03) {

        profileService.userSpoofCheck(deviceId, image01, image02, image03);
    }

}
