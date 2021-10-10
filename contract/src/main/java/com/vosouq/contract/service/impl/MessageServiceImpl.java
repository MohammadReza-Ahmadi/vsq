package com.vosouq.contract.service.impl;

import com.vosouq.contract.model.CreateMessageRequest;
import com.vosouq.contract.model.SubjectSubType;
import com.vosouq.contract.service.MessageService;
import com.vosouq.contract.service.feign.MessageServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageServiceClient messageServiceClient;

    public MessageServiceImpl(MessageServiceClient messageServiceClient) {

        this.messageServiceClient = messageServiceClient;
    }

    @Override
    public void send(Long userId, String message, Long subjectId, String subjectType, SubjectSubType subjectSubType) {
        try {
            messageServiceClient.createMessage(new CreateMessageRequest(
                    userId,
                    message,
                    subjectId,
                    subjectType,
                    subjectSubType));

        } catch (Throwable throwable) {
            log.error("error sending message - ", throwable);
        }
    }

}
