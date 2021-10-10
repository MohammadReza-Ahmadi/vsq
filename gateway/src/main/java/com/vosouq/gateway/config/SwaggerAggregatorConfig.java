package com.vosouq.gateway.config;

import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerAggregatorConfig {

    private final ZuulProperties properties;

    public SwaggerAggregatorConfig(ZuulProperties properties) {
        this.properties = properties;
    }

    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            List<SwaggerResource> resources = new ArrayList<>();
            createCustomResources(resources);
            properties.getRoutes().values().forEach(route -> resources.add(createResourceFromRoute(route.getServiceId(), route.getId())));
            return resources;
        };
    }

    private SwaggerResource createResourceFromRoute(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation("/" + location + "/v2/api-docs");
        return swaggerResource;
    }

    private void createCustomResources(List<SwaggerResource> resources) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName("auth");
        swaggerResource.setLocation("/v2/api-docs");
        resources.add(swaggerResource);
    }

}
