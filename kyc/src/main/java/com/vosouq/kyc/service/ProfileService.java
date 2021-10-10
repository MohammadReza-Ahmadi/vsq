package com.vosouq.kyc.service;

import com.vosouq.kyc.model.User;

public interface ProfileService {

    void addKycInfo(String nationalCode, String serial, String birthDate, String firstName, String lastName);

    User completeKycInfo(String profileImageAddress);

}
