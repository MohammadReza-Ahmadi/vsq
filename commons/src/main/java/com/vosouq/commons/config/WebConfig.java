package com.vosouq.commons.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebConfig {

    @Autowired
    public void configureDispatcher(DispatcherServlet dispatcherServlet) {
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    }

}
