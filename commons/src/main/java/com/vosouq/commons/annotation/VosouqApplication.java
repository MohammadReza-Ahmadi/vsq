package com.vosouq.commons.annotation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootApplication(scanBasePackages = "com.vosouq")
@EnableFeignClients
public @interface VosouqApplication {
}