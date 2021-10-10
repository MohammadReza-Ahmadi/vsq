package com.vosouq.scoringcommunicator.services;

import com.vosouq.scoringcommunicator.controllers.dtos.res.CredentialCheckRequestRes;
import com.vosouq.scoringcommunicator.models.CredentialCheckRequest;
import com.vosouq.scoringcommunicator.models.RequestType;

import java.util.List;

public interface CredentialCheckRequestService {

    void createRequestForAcceptorUser(CredentialCheckRequest ccr);

    void createRequestForAcceptorUser(Long acceptorId);

    void updateForAccept(String id);

    void updateForReject(String id);

    void updateForInvisibility(String id);

    List<CredentialCheckRequestRes> findAllRequests(RequestType type);

    List<CredentialCheckRequest> findAllReceivedRequests();

    boolean hasAcceptedNotExpiredRequestForOtherUser(Long otherUserId);

    CredentialCheckRequest getOnlineUserRequestForOtherUser(Long userId);
}
