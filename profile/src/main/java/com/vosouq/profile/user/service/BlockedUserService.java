package com.vosouq.profile.user.service;

import com.vosouq.profile.user.model.BlockedUser;

import java.util.Optional;

public interface BlockedUserService {

    Optional<BlockedUser> get(String phoneNumber, String udid);

    void blockUser(Long userId, String phoneNumber, Long deviceId, String udid);
}
