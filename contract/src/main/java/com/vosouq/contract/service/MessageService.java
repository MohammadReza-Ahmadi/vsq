package com.vosouq.contract.service;

import com.vosouq.contract.model.SubjectSubType;

public interface MessageService {

    void send(Long userId, String message, Long subjectId, String subjectType, SubjectSubType subjectSubType);

}