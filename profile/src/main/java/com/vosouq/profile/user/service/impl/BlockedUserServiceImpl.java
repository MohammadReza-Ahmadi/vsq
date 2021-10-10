package com.vosouq.profile.user.service.impl;

import com.vosouq.profile.user.exception.TooManySmsCodeTryException;
import com.vosouq.profile.user.model.BlockedUser;
import com.vosouq.profile.user.repository.BlockedUserRepository;
import com.vosouq.profile.user.service.BlockedUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class BlockedUserServiceImpl implements BlockedUserService {

    @Value("${registration.otp.block-duration-in-seconds}")
    private int otpBlockDurationInSeconds;
    private final BlockedUserRepository blockedUserRepository;

    public BlockedUserServiceImpl(BlockedUserRepository blockedUserRepository) {
        this.blockedUserRepository = blockedUserRepository;
    }

    @Override
    public Optional<BlockedUser> get(String phoneNumber, String udid) {
        return blockedUserRepository.findByPhoneNumberAndUdidAndBlockUntilGreaterThanEqual(phoneNumber, udid, new Date());
    }

    @Override
    @Transactional(noRollbackFor = TooManySmsCodeTryException.class)
    public void blockUser(Long userId, String phoneNumber, Long deviceId, String udid) {

        BlockedUser blockedUser = new BlockedUser();
        blockedUser.setUserId(userId);
        blockedUser.setPhoneNumber(phoneNumber);
        blockedUser.setDeviceId(deviceId);
        blockedUser.setUdid(udid);
        blockedUser.setCreateDate(new Date());
        blockedUser.setBlockUntil(new Date(System.currentTimeMillis() + (otpBlockDurationInSeconds * 1000)));

        blockedUserRepository.saveAndFlush(blockedUser);
    }

}
