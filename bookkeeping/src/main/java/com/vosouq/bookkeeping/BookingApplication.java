package com.vosouq.bookkeeping;

import com.vosouq.commons.annotation.VosouqApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@VosouqApplication
@EnableJpaAuditing
@EnableJpaRepositories(considerNestedRepositories = true)
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

}
