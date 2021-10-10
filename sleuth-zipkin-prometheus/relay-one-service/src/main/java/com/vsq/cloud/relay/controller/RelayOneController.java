package com.vsq.cloud.relay.controller;

import com.vsq.cloud.relay.service.feign.RelayTwoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RelayOneController {
    private static final Logger logger = LoggerFactory.getLogger(RelayOneController.class);
    private final RelayTwoService relayTwoService;

    public RelayOneController(RelayTwoService relayTwoService) {
        this.relayTwoService = relayTwoService;
    }

    @GetMapping("/test")

    public String test(@RequestParam String param){
        logger.info("forwarding request to relay-two");
        return relayTwoService.test(param);
    }
}
