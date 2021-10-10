package com.vosouq.gateway.repository;

import com.vosouq.gateway.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findAllByUserIdAndDeviceId(Long userId, Long deviceId);

    List<Token> findAllByUserId(Long userId);
}
