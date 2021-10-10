package com.vosouq.contract.service.impl;

import com.vosouq.contract.model.User;
import com.vosouq.contract.service.ProfileService;
import com.vosouq.contract.service.feign.ProfileServiceClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final ProfileServiceClient profileServiceClient;

    public ProfileServiceImpl(ProfileServiceClient profileServiceClient) {
        this.profileServiceClient = profileServiceClient;
    }

    @Override
    public User find(Long id) {
        return profileServiceClient.findById(id);
    }
}
