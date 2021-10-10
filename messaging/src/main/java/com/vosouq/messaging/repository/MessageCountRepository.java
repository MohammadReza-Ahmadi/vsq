package com.vosouq.messaging.repository;

import com.vosouq.messaging.model.MessageCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageCountRepository extends JpaRepository<MessageCount, Long> {

    Optional<MessageCount> findByUserId(Long userId);

}
