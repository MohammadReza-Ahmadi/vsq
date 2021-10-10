package com.vosouq.messaging;

import com.vosouq.commons.annotation.VosouqApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@VosouqApplication
@EnableAsync
public class MessagingApplication {
    public static void main(String[] args) {
        SpringApplication.run(MessagingApplication.class, args);
    }
}
