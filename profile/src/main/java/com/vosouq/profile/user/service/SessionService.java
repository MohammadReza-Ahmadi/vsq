package com.vosouq.profile.user.service;

import com.vosouq.profile.user.model.Device;
import com.vosouq.profile.user.model.User;

public interface SessionService {

    void create(User user, Device device);

}
