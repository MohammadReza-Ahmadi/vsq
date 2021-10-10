package com.vosouq.messaging.service;

import com.vosouq.messaging.model.Message;
import com.vosouq.messaging.model.SubjectSubType;

import java.util.List;

public interface MessageService {

    List<Message> findAll(Long userID, int page, int pageSize);

    List<Message> findAll(Long userID, Long fromMessageId, int page, int pageSize);

    int getCount(Long userId);

    void create(Long userId, String content, Long subjectId, String subjectType, SubjectSubType subjectSubType);

}
