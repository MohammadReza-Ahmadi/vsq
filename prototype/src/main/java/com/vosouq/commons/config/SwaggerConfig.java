package com.vosouq.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.AllowableListValues;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vosouq"))
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(getApiInfo())
                .globalOperationParameters(Arrays.asList(getAcceptLanguageHeader()));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("VOSOUQ REST API")
                .description("OPEN REST APIs OF VOSOUQ")
                .license("License Dedicated to VOSOUQ")
                .version("0.0.1-SNAPSHOT")
                .build();
    }

    private Parameter getAcceptLanguageHeader() {
        return new ParameterBuilder()
                .name("Accept-Language")
                .parameterType("header")
                .modelRef(new ModelRef("String"))
                .description("acceptable language by client")
                .allowableValues(new AllowableListValues(Arrays.asList("fa", "en"), "String"))
                .defaultValue("fa")
                .allowMultiple(false)
                .required(false)
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
