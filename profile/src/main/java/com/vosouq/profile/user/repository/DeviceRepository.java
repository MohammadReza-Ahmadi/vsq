package com.vosouq.profile.user.repository;

import com.vosouq.profile.user.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findAllByUserId(Long userId);

    List<Device> findAllByFcmToken(String fcmToken);

    Optional<Device> findByUserIdAndUdid(Long userId, String udid);

    Optional<Device> findByUdid(String udid);
}
