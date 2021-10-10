package com.vosouq.commons.config;

import com.vosouq.commons.model.OnlineUser;
import com.vosouq.commons.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Configuration
@Slf4j
public class OnlineUserConfig {

    @Value("${service.message.code}")
    private String serviceMessageCode;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public OnlineUser createSession(HttpServletRequest request, HttpServletResponse response) {

        try {
            if (request.getHeader("secret") != null) {

                String secret = request.getHeader("secret");

                String userId = JwtTokenUtil.getUserIdFromToken(secret);
                Map<String, Object> map = JwtTokenUtil.getClaimFromToken(secret, claims -> claims);

                OnlineUser onlineUser = new OnlineUser();
                onlineUser.setUserId(Long.parseLong(userId));
                onlineUser.setDeviceId(Long.parseLong(String.valueOf(map.get("deviceId"))));
                onlineUser.setPhoneNumber(String.valueOf(map.get("phoneNumber")));
                onlineUser.setClientId(String.valueOf(map.get("clientId")));
                onlineUser.setClientName(String.valueOf(map.get("clientName")));

                return onlineUser;

            } else if (request.getAttribute("onlineUser") != null) {
                return (OnlineUser) request.getAttribute("onlineUser");
            }
        } catch (Throwable throwable) {
            log.error("", throwable);
        }

        return null;

    }

}
