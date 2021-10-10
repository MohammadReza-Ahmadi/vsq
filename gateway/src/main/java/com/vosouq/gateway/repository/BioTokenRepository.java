package com.vosouq.gateway.repository;

import com.vosouq.gateway.model.BioToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BioTokenRepository extends JpaRepository<BioToken, Long> {

    List<BioToken> findAllByUserIdAndDeviceId(Long userId, Long deviceId);
}
