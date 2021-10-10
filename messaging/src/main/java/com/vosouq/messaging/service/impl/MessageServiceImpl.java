package com.vosouq.messaging.service.impl;

import com.vosouq.messaging.model.Message;
import com.vosouq.messaging.model.MessageCount;
import com.vosouq.messaging.model.SubjectSubType;
import com.vosouq.messaging.repository.MessageCountRepository;
import com.vosouq.messaging.repository.MessageRepository;
import com.vosouq.messaging.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final MessageCountRepository messageCountRepository;

    public MessageServiceImpl(MessageRepository messageRepository, MessageCountRepository messageCountRepository) {
        this.messageRepository = messageRepository;
        this.messageCountRepository = messageCountRepository;
    }

    @Override
    public List<Message> findAll(Long userID, int page, int pageSize) {
        List<Message> messages = messageRepository.findAllByUserIdOrderByIdDesc(
                userID,
                PageRequest.of(page - 1, pageSize));

        Optional<MessageCount> messageCountOptional = messageCountRepository.findByUserId(userID);
        messageCountOptional.ifPresent(messageCount -> {
            messageCount.setCount(0);
            messageCountRepository.save(messageCount);
        });

        return messages;
    }

    @Override
    public List<Message> findAll(Long userID, Long fromMessageId, int page, int pageSize) {
        List<Message> messages = messageRepository.findAllByUserIdAndIdGreaterThanOrderByIdDesc(
                userID,
                fromMessageId,
                PageRequest.of(page - 1, pageSize));

        Optional<MessageCount> messageCountOptional = messageCountRepository.findByUserId(userID);
        messageCountOptional.ifPresent(messageCount -> {
            messageCount.setCount(0);
            messageCountRepository.save(messageCount);
        });

        return messages;
    }

    @Override
    public int getCount(Long userId) {
        Optional<MessageCount> messageCountOptional = messageCountRepository.findByUserId(userId);
        return messageCountOptional.orElse(new MessageCount(0)).getCount();
    }

    @Override
    @Async
    public void create(Long userId, String content, Long subjectId, String subjectType, SubjectSubType subjectSubType) {
        Message message = new Message();
        message.setContent(content);
        message.setUserId(userId);
        message.setSubjectId(subjectId);
        message.setSubjectType(subjectType);
        message.setCreateDate(new Timestamp(System.currentTimeMillis()));
        message.setSubjectSubType(subjectSubType != null ? subjectSubType : SubjectSubType.EMPTY);
        messageRepository.save(message);

        Optional<MessageCount> messageCountOptional = messageCountRepository.findByUserId(userId);
        MessageCount messageCount;
        if (messageCountOptional.isPresent()) {
            messageCount = messageCountOptional.get();
            messageCount.setCount(messageCount.getCount() + 1);
        } else {
            messageCount = new MessageCount();
            messageCount.setCount(1);
            messageCount.setUserId(userId);
        }
        messageCountRepository.save(messageCount);
    }

}
