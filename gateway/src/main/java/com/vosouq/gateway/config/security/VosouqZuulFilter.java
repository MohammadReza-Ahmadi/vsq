package com.vosouq.gateway.config.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.commons.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Component
@Slf4j
public class VosouqZuulFilter extends ZuulFilter {

    @Value("${service.message.code}")
    private String serviceMessageCode;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();
        OnlineUser onlineUser = (OnlineUser) request.getAttribute("onlineUser");

        if (onlineUser != null) {

            String secret = JwtTokenUtil.generateToken(
                    String.valueOf(onlineUser.getUserId()),
                    onlineUser.getDeviceId(),
                    onlineUser.getPhoneNumber(),
                    onlineUser.getClientId(),
                    onlineUser.getClientName());

            ctx.addZuulRequestHeader("secret", secret);
            return ctx;
        }

        String path = request.getRequestURI();
        if (path.contains("api-docs")) {
            String secret = JwtTokenUtil.generateToken("-1", new HashMap<>());
            ctx.addZuulRequestHeader("secret", secret);
            return ctx;
        }

        return null;
    }
}
