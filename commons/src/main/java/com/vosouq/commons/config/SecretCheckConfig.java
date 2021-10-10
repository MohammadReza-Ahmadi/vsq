package com.vosouq.commons.config;

import com.vosouq.commons.util.JwtTokenUtil;
import com.vosouq.commons.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@Slf4j
public class SecretCheckConfig /*implements Filter*/ {

//    @Value("${spring.application.name}")
//    private String applicationName;
//
//    @Value("${service.message.code}")
//    private String serviceMessageCode;
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse,
//                         FilterChain chain) throws IOException, ServletException {
//
//        if (!applicationName.equals("gateway")
//                && !applicationName.equals("bookkeeping")
//                && !applicationName.equals("payment-gateway")
//                && !applicationName.equals("contract")
//                && !applicationName.equals("messaging")
//                && !applicationName.equals("profile")
//                && !applicationName.equals("kyc")
//        ) {
//
//            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//
//            try {
//                if (httpServletRequest.getHeader("secret") == null ||
//                        JwtTokenUtil.isTokenExpired(httpServletRequest.getHeader("secret")) ||
//                        JwtTokenUtil.getUserIdFromToken(httpServletRequest.getHeader("secret")) == null) {
//
//                    log.error("secret {} is invalid", httpServletRequest.getHeader("secret"));
//                    throw new Exception();
//                }
//            } catch (Throwable throwable) {
//                try {
//                    MessageUtil.flushErrorMessage(httpServletRequest, httpServletResponse, serviceMessageCode, HttpStatus.FORBIDDEN);
//                } catch (IOException e) {
//                    log.error("error flushing security exception JSON.", e.getCause());
//                }
//            }
//
//        }
//
//        chain.doFilter(servletRequest, servletResponse);
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
}
