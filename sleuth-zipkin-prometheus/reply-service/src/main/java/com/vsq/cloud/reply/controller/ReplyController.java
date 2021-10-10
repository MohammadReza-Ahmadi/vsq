package com.vsq.cloud.reply.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyController {

    @GetMapping("/reply")
    public String reply(@RequestParam String message){
        return "Received: " + message;
    }
}
