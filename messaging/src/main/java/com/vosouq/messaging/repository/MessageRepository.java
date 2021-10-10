package com.vosouq.messaging.repository;

import com.vosouq.messaging.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByUserIdOrderByIdDesc(Long userId, Pageable pageable);

    List<Message> findAllByUserIdAndIdGreaterThanOrderByIdDesc(Long UserId, Long id, Pageable pageable);
}
