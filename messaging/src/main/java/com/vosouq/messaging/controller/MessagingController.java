package com.vosouq.messaging.controller;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.messaging.model.Message;
import com.vosouq.messaging.model.SubjectSubType;
import com.vosouq.messaging.service.MessageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@VosouqRestController
@Slf4j
public class MessagingController {

    private final OnlineUser onlineUser;
    private final MessageService messageService;

    public MessagingController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") OnlineUser onlineUser,
                               MessageService messageService) {

        this.onlineUser = onlineUser;
        this.messageService = messageService;
    }

    @GetMapping
    @ApiOperation(value = "returns all messages, starting from 'fromId'")
    public List<MessageResponse> getAll(
            @RequestParam(required = false, defaultValue = "")
            @ApiParam(value = "the id of the message to offset from response list")
                    Long fromId,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "25") @Min(1) @Max(200) Integer pageSize

    ) {
        List<Message> messages;
        if (fromId == null) {
            messages = messageService.findAll(onlineUser.getUserId(), page, pageSize);
        } else {
            messages = messageService.findAll(onlineUser.getUserId(), fromId, page, pageSize);
        }
        return messages
                .stream()
                .map(message -> new MessageResponse(
                        message.getId(),
                        message.getContent(),
                        message.getSubjectId(),
                        message.getSubjectType(),
                        message.getSubjectSubType() != null ? message.getSubjectSubType() : SubjectSubType.EMPTY))
                .collect(Collectors.toList());
    }

    @GetMapping("/count")
    public MessageCountResponse getCount() {
        return new MessageCountResponse(messageService.getCount(onlineUser.getUserId()));
    }

    @PostMapping
    @Created
    public void create(@Valid @RequestBody CreateMessageRequest request) {
        messageService.create(request.getUserId(),
                request.getContent(),
                request.getSubjectId(),
                request.getSubjectType(),
                request.getSubjectSubType()
        );
    }
}
