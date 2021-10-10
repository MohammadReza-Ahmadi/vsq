package com.vosouq.profile.user.repository;

import com.vosouq.profile.user.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    
    List<Session> findAllByUserId(Long userId);

    @Query(value = "select count(distinct s.deviceId) from Session s where s.userId = :userId")
    int findCountOfActiveSessionsByUserId(Long userId);
}
