package com.vosouq.profile.user.service.impl;

import com.vosouq.profile.user.model.Device;
import com.vosouq.profile.user.model.Session;
import com.vosouq.profile.user.model.User;
import com.vosouq.profile.user.repository.SessionRepository;
import com.vosouq.profile.user.service.SessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void create(User user, Device device) {

        int numberOfActiveSessions = getNumberOfActiveSessions(user.getId());

//        if (numberOfActiveSessions < 3) {
        create(user.getId(),
                device.getId(),
                device.getName(),
                device.getOs(),
                device.getOsVersion(),
                device.getAppVersion(),
                device.getFcmToken());
//        } else {
//            throw new TooManySessionsException();
//        }
    }

    private void create(Long userId,
                        Long deviceId,
                        String deviceName,
                        String os,
                        String osVersion,
                        String appVersion,
                        String fcmToken) {

        Session session = new Session();
        session.setUserId(userId);
        session.setDeviceId(deviceId);
        session.setDeviceName(deviceName);
        session.setOs(os);
        session.setOsVersion(osVersion);
        session.setAppVersion(appVersion);
        session.setFcmToken(fcmToken);
        session.setDate(new Date());

        sessionRepository.save(session);
    }

    private int getNumberOfActiveSessions(Long userId) {
        return sessionRepository.findCountOfActiveSessionsByUserId(userId);
    }

}
