package com.vosouq.contract.service.feign;

import com.vosouq.contract.model.CreateMessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "messaging")
public interface MessageServiceClient {

    @PostMapping
    void createMessage(@RequestBody CreateMessageRequest request);
}
