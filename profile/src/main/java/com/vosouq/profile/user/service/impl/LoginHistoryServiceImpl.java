package com.vosouq.profile.user.service.impl;

import com.vosouq.profile.user.model.LoginAction;
import com.vosouq.profile.user.model.LoginHistory;
import com.vosouq.profile.user.repository.LoginHistoryRepository;
import com.vosouq.profile.user.service.LoginHistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class LoginHistoryServiceImpl implements LoginHistoryService {
    
    private final LoginHistoryRepository loginHistoryRepository;
    
    public LoginHistoryServiceImpl(LoginHistoryRepository loginHistoryRepository) {
        this.loginHistoryRepository = loginHistoryRepository;
    }

    @Override
    public LoginHistory create(long userId,
                               long deviceId,
                               String deviceName,
                               String phoneNumber,
                               LoginAction action) {

        LoginHistory loginHistory = new LoginHistory();
        loginHistory.setUserId(userId);
        loginHistory.setDeviceId(deviceId);
        loginHistory.setDeviceName(deviceName);
        loginHistory.setPhoneNumber(phoneNumber);
        loginHistory.setAction(action);
        loginHistory.setDate(new Date());

        return loginHistoryRepository.save(loginHistory);
    }
}
