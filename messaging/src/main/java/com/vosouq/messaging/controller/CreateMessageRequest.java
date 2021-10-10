package com.vosouq.messaging.controller;

import com.vosouq.messaging.model.SubjectSubType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageRequest {

    private Long userId;
    private String content;
    private Long subjectId;
    private String subjectType;
    private SubjectSubType subjectSubType;

}
