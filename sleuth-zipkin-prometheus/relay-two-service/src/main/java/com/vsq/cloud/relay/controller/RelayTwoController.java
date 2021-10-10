package com.vsq.cloud.relay.controller;

import com.vsq.cloud.relay.service.feign.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelayTwoController {
    private static final Logger logger = LoggerFactory.getLogger(RelayTwoController.class);
    private final ReplyService replyService;

    public RelayTwoController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/test")
    String test(@RequestParam String param){
        logger.info("forwarding request to reply.");
        return replyService.reply(param);
    }
}
