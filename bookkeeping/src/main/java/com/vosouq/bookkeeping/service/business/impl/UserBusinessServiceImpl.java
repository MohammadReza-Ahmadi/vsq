package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.commons.model.OnlineUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserBusinessServiceImpl implements UserBusinessService {

    private final OnlineUser onlineUser;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public UserBusinessServiceImpl(OnlineUser onlineUser) {
        this.onlineUser = onlineUser;
    }

    @Override
    public Long getCurrentUserId() {
        Long userId = onlineUser.getUserId();
        return userId;
    }
}
