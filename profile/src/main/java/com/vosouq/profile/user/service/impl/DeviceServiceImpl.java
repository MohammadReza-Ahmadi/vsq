package com.vosouq.profile.user.service.impl;

import com.vosouq.commons.util.MessageUtil;
import com.vosouq.profile.user.exception.DeviceNotFoundException;
import com.vosouq.profile.user.model.Device;
import com.vosouq.profile.user.repository.DeviceRepository;
import com.vosouq.profile.user.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device create(Long userId,
                         String udid,
                         String name,
                         String os,
                         String osVersion,
                         String appVersion) {

        Optional<Device> deviceOptional = deviceRepository.findByUserIdAndUdid(userId, udid);

        return deviceOptional.orElseGet(() -> {
            Device device = new Device();
            device.setUdid(udid);
            device.setName(name);
            device.setOs(os);
            device.setOsVersion(osVersion);
            device.setAppVersion(appVersion);
            device.setCreateDate(new Date());
            device.setUserId(userId);

            log.info("deviceId: {} created successfully", device.getId());

            return deviceRepository.save(device);
        });

    }

    @Override
    public Device get(Long id) {
        return deviceRepository
                .findById(id)
                .orElseThrow(DeviceNotFoundException::new);
    }

    @Override
    public Device save(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device get(String udid, Long userId) {
        return deviceRepository
                .findByUserIdAndUdid(userId, udid)
                .orElseThrow(DeviceNotFoundException::new);
    }

    @Override
    public List<Device> getAll(Long userId) {
        return deviceRepository.findAllByUserId(userId);
    }

    @Override
    public void registerFcmToken(Long deviceId, String token) {

        List<Device> devices = deviceRepository.findAllByFcmToken(token);
        devices.forEach(device -> {
            device.setFcmToken("");
            deviceRepository.save(device);
        });

        Device device = get(deviceId);
        device.setFcmToken(token);
        deviceRepository.save(device);
    }

    private String getPushToken(Device device, String fcmToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson="";

        HttpEntity<?> entity = new HttpEntity<>(requestJson, headers);

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> response = restTemplate.postForEntity(
                "https://onesignal.com/api/v1/players",
                entity,
                String.class);
        return "";
    }

}
