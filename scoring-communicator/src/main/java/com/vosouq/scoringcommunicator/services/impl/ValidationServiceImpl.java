package com.vosouq.scoringcommunicator.services.impl;

import com.vosouq.scoringcommunicator.infrastructures.exceptions.CreateCredentialCheckRequestIsNotAllowedException;
import com.vosouq.scoringcommunicator.infrastructures.exceptions.CredentialProfileNotAccessibleException;
import com.vosouq.scoringcommunicator.infrastructures.utils.CreditScoringUtil;
import com.vosouq.scoringcommunicator.services.CredentialCheckRequestService;
import com.vosouq.scoringcommunicator.services.CredentialProfileService;
import com.vosouq.scoringcommunicator.services.ValidationService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements ValidationService {

    private CredentialProfileService credentialProfileService;
    private CredentialCheckRequestService credentialCheckRequestService;

    public ValidationServiceImpl(CredentialProfileService credentialProfileService, @Lazy CredentialCheckRequestService credentialCheckRequestService) {
        this.credentialProfileService = credentialProfileService;
        this.credentialCheckRequestService = credentialCheckRequestService;
    }

    @Override
    public void validateUserAccess(Long otherUserId) {
        // check other profile has public view
        if (CreditScoringUtil.isTrue(credentialProfileService.getUserProfile(otherUserId).getPublicView()))
            return;

        // check onlineUser has accepted not expired request from otherUser
        if (credentialCheckRequestService.hasAcceptedNotExpiredRequestForOtherUser(otherUserId))
            return;
        throw new CredentialProfileNotAccessibleException();
    }

    @Override
    public void validateCreateCredentialCheckRequest(Long otherUserId) {
        if (CreditScoringUtil.isTrue(credentialProfileService.getUserProfile(otherUserId).getPublicView()))
            throw new CreateCredentialCheckRequestIsNotAllowedException();

    }
}
