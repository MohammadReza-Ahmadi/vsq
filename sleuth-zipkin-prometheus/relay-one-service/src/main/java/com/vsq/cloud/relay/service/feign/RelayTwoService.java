package com.vsq.cloud.relay.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "relay-two-service", path = "/")
public interface RelayTwoService {

    @GetMapping("/test")
    String test(@RequestParam String param);
}
