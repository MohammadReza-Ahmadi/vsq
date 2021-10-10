package com.vosouq.profile.user.service;

import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.profile.user.model.RegistrationSms;

public interface RegistrationSmsService {
    
    BinaryDataHolder<RegistrationSms, Boolean> create(Long userId, Long deviceId, Long expireDurationInMillis);

    void evaluateSms(Long deviceId, String smsCode);

}
