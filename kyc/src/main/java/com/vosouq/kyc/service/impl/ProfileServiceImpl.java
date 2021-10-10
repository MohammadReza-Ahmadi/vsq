package com.vosouq.kyc.service.impl;

import com.vosouq.kyc.model.AddKycInfoRequest;
import com.vosouq.kyc.model.CompleteKycInfoRequest;
import com.vosouq.kyc.model.User;
import com.vosouq.kyc.repository.ProfileRepository;
import com.vosouq.kyc.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void addKycInfo(String nationalCode, String serial, String birthDate, String firstName, String lastName) {
        AddKycInfoRequest addKycInfoRequest = new AddKycInfoRequest(nationalCode, serial, birthDate, firstName, lastName);
        profileRepository.addKycInfo(addKycInfoRequest);
    }

    @Override
    public User completeKycInfo(String profileImageAddress) {
        return profileRepository.completeKycStatus(new CompleteKycInfoRequest(profileImageAddress));
    }


}
