package com.vosouq.profile.user.service;

import com.vosouq.profile.user.model.Registration;
import com.vosouq.profile.user.model.User;

public interface UserService {

    Registration register(String udid,
                          String deviceName,
                          String os,
                          String osVersion,
                          String appVersion,
                          String phoneNumber);

    User verify(Long deviceId, String smsCode);

    User login(Long deviceId, String password, String bioToken);

    User bioLogin(Long deviceId, String currentBioToken, String newBioToken);

    User setPassword(Long deviceId,
                     String password,
                     String confirmPassword,
                     String bioToken);

    User get(Long id);

    User getByDeviceId(Long deviceId);

    User get(String phoneNumber);

    User getByPhoneNumber(String phoneNumber);

    User completeKycStatus(Long userId, String profileImageAddress);

    void addKycInfo(Long userId,
                    String nationalCode,
                    String serial,
                    String birthDate,
                    String firstName,
                    String lastName);

}
