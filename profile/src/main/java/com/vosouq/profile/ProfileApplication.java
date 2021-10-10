package com.vosouq.profile;

import com.vosouq.commons.annotation.VosouqApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@VosouqApplication
@EnableAsync
public class ProfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfileApplication.class, args);
    }

}
