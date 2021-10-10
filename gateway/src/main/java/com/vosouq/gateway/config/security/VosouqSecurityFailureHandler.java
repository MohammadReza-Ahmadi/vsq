package com.vosouq.gateway.config.security;

import com.vosouq.commons.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class VosouqSecurityFailureHandler implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Value("${service.message.code}")
    private String serviceMessageCode;

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {

        log.error("VosouqSecurityFailureHandler - Authorization Error(401 UNAUTHORIZED) ", e);
        MessageUtil.flushErrorMessage(httpServletRequest, httpServletResponse, serviceMessageCode, HttpStatus.UNAUTHORIZED);
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {

        log.error("VosouqSecurityFailureHandler - Access Denied(403 FORBIDDEN) ", e);
        MessageUtil.flushErrorMessage(httpServletRequest, httpServletResponse, serviceMessageCode, HttpStatus.FORBIDDEN);
    }

}
