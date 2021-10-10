package com.vosouq.scoringcommunicator.services;

import com.vosouq.scoringcommunicator.models.CredentialProfile;

public interface CredentialProfileService {

    CredentialProfile initializeProfile(Long userId);

    void updateProfilePublicView(int publicity);

    CredentialProfile getUserProfile(Long userId);
}
