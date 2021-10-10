package com.vosouq.contract.service;

public interface NotificationService {

    void send(Long userId, String body, Long subjectId, String subjectType);
}