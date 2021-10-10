package com.vosouq.gateway.service;

import com.vosouq.gateway.model.Login;
import com.vosouq.gateway.model.RegisterUserResponse;
import com.vosouq.gateway.model.Verify;
import com.vosouq.gateway.model.VerifyUserKyc;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    RegisterUserResponse register(String udid,
                                  String deviceName,
                                  String os,
                                  String osVersion,
                                  String appVersion,
                                  String phoneNumber);

    Verify verify(Long deviceId, String smsCode);

    Login createPassword(Long deviceId, String password, String confirmPassword);

    Login login(Long deviceId, String password);

    Login bioLogin(Long deviceId, String currentBioToken);

    VerifyUserKyc verifyUserKyc(Long deviceId, String method, MultipartFile video);

    void userSpoofCheck(Long deviceId,
                        MultipartFile image01,
                        MultipartFile image02,
                        MultipartFile image03);

}
