package com.vosouq.scoringcollector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
@SpringBootApplication(scanBasePackages = {"com.vosouq.*"})
public class ScoringCollectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoringCollectorApplication.class, args);
    }
}
