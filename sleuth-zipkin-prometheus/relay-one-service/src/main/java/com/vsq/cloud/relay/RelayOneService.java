package com.vsq.cloud.relay;

import brave.sampler.Sampler;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class RelayOneService {
    public static void main(String[] args) {
        SpringApplication.run(RelayOneService.class, args);
    }

//    @Bean
//    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
//        return registry -> registry.config().commonTags("application", "RelayOneService");
//    }

//    @Bean
//    public Sampler defaultSampler() {
//        return new Sampler() {
//            @Override
//            public boolean isSampled(long l) {
//                return true;
//            }
//        };
//    }

}
