package com.vosouq.profile.user.service;

import com.vosouq.profile.user.model.LoginAction;
import com.vosouq.profile.user.model.LoginHistory;

public interface LoginHistoryService {
    
    LoginHistory create(long userId,
                        long deviceId,
                        String deviceName,
                        String phoneNumber,
                        LoginAction action);
    
}
