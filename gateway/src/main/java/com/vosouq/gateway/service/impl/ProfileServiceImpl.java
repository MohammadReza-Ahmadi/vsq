package com.vosouq.gateway.service.impl;

import com.vosouq.gateway.config.security.TokenStore;
import com.vosouq.gateway.model.*;
import com.vosouq.gateway.repository.KycRepository;
import com.vosouq.gateway.repository.ProfileRepository;
import com.vosouq.gateway.service.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final TokenStore tokenStore;
    private final KycRepository kycRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository,
                              TokenStore tokenStore,
                              KycRepository kycRepository) {

        this.profileRepository = profileRepository;
        this.tokenStore = tokenStore;
        this.kycRepository = kycRepository;
    }

    @Override
    public RegisterUserResponse register(String udid,
                                         String deviceName,
                                         String os,
                                         String osVersion,
                                         String appVersion,
                                         String phoneNumber) {

        RegisterUserRequest registerUserRequest = new RegisterUserRequest(
                udid,
                deviceName,
                os,
                osVersion,
                appVersion,
                phoneNumber);

        return profileRepository.register(registerUserRequest);
    }

    @Override
    public Verify verify(Long deviceId, String smsCode) {

        VerifyUserRequest verifyUserRequest = new VerifyUserRequest(deviceId, smsCode);

        VerifyUserResponse verifyUserResponse = profileRepository.verify(verifyUserRequest);

        /*
         * when password is not defined and user not KYCed yet, then a limited token will be generated for user
         */
        if (!verifyUserResponse.isPasswordDefined() && !verifyUserResponse.isKycCompleted()) {
            Token token = tokenStore.createToken(
                    verifyUserResponse.getUserId(),
                    deviceId,
                    verifyUserResponse.getPhoneNumber(),
                    false);

            return new Verify(
                    token.getToken(),
                    token.getTokenType(),
                    verifyUserResponse.isKycCompleted(),
                    verifyUserResponse.isPasswordDefined());
        }

        return new Verify(
                verifyUserResponse.isKycCompleted(),
                verifyUserResponse.isPasswordDefined());

    }

    @Override
    public VerifyUserKyc verifyUserKyc(Long deviceId, String method, MultipartFile video) {
        UserInfoResponse userInfoResponse = profileRepository.getUserInfoByDeviceId(deviceId);

        kycRepository.verifyUserKyc(userInfoResponse.getNationalCode(), method, video);

        Token token = tokenStore.createToken(
                userInfoResponse.getUserId(),
                deviceId,
                userInfoResponse.getPhoneNumber(),
                false);

        return new VerifyUserKyc(token.getToken(), token.getTokenType());
    }

    @Override
    public void userSpoofCheck(Long deviceId, MultipartFile image01, MultipartFile image02, MultipartFile image03) {
        UserInfoResponse userInfoResponse = profileRepository.getUserInfoByDeviceId(deviceId);
        kycRepository.userSpoofCheck(userInfoResponse.getNationalCode(), image01, image02, image03);
    }

    @Override
    public Login createPassword(Long deviceId, String password, String confirmPassword) {

        String bioToken = UUID.randomUUID().toString();
        CreatePasswordRequest createPasswordRequest = new CreatePasswordRequest(deviceId, password, confirmPassword, bioToken);
        LoginUserResponse loginUserResponse = profileRepository.createPassword(createPasswordRequest);

        return login(
                loginUserResponse.getUserId(),
                deviceId,
                loginUserResponse.getPhoneNumber(),
                bioToken);
    }

    @Override
    public Login login(Long deviceId, String password) {

        String bioToken = UUID.randomUUID().toString();
        LoginUserRequest loginUserRequest = new LoginUserRequest(deviceId, password, bioToken);
        LoginUserResponse loginUserResponse = profileRepository.login(loginUserRequest);

        return login(
                loginUserResponse.getUserId(),
                deviceId,
                loginUserResponse.getPhoneNumber(),
                bioToken);
    }

    @Override
    public Login bioLogin(Long deviceId, String currentBioToken) {

        String newBioToken = UUID.randomUUID().toString();
        BioLoginUserRequest bioLoginUserRequest = new BioLoginUserRequest(deviceId, currentBioToken, newBioToken);
        LoginUserResponse loginUserResponse = profileRepository.bioLogin(bioLoginUserRequest);

        return login(
                loginUserResponse.getUserId(),
                deviceId,
                loginUserResponse.getPhoneNumber(),
                newBioToken);
    }

    private Login login(Long userId,
                        Long deviceId,
                        String phoneNumber,
                        String biomedicalToken) {

        tokenStore.revokeToken(userId, deviceId);
        tokenStore.revokeBioToken(userId, deviceId);

        Token token = tokenStore.createToken(
                userId,
                deviceId,
                phoneNumber,
                true);

        BioToken bioToken = tokenStore.createBioToken(
                userId,
                deviceId,
                phoneNumber,
                biomedicalToken);

        return new Login(token.getToken(), bioToken.getToken());

    }

}
