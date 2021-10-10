package com.vosouq.profile.user.service;

import com.vosouq.profile.user.model.Device;

import java.util.List;

public interface DeviceService {
    
    Device create(Long userId,
                  String udid,
                  String name,
                  String os,
                  String osVersion,
                  String appVersion);

    Device get(Long id);

    Device save(Device device);

    Device get(String udid, Long userId);

    List<Device> getAll(Long userId);

    void registerFcmToken(Long deviceId, String token);
    
}
