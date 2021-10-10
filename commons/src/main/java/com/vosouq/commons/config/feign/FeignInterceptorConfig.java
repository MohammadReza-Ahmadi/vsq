package com.vosouq.commons.config.feign;

import com.vosouq.commons.model.OnlineUser;
import com.vosouq.commons.util.JwtTokenUtil;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FeignInterceptorConfig {

    private final OnlineUser onlineUser;

    public FeignInterceptorConfig(OnlineUser onlineUser) {
        this.onlineUser = onlineUser;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            try {
                OnlineUser onlineUser = this.onlineUser.clone();

                String secret = JwtTokenUtil.generateToken(
                        String.valueOf(onlineUser.getUserId()),
                        onlineUser.getDeviceId(),
                        onlineUser.getPhoneNumber(),
                        onlineUser.getClientId(),
                        onlineUser.getClientName());

                requestTemplate.header("secret", secret);

            } catch (Throwable throwable) {
                log.error("", throwable);
            }

        };
    }

}
