package com.vosouq.profile.user.repository;

import com.vosouq.profile.user.model.RegistrationSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface RegistrationSmsRepository extends JpaRepository<RegistrationSms, Long> {

    Optional<RegistrationSms> findByDeviceIdAndExpireDateGreaterThanEqual(Long deviceId, Date date);

}
