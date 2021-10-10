package com.vosouq.paymentgateway.mellat.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        /**
        registry.addResourceHandler(
                "/**",
                "/ipg/mellat/**",
                "/callbacks/ipg/**",
                "/callbacks/ipg/mellat/**"
        )
                .addResourceLocations("classpath:/static/");
    }
}
