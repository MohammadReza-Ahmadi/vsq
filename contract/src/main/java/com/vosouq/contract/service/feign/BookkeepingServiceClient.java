package com.vosouq.contract.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "bookkeeping")
public interface BookkeepingServiceClient {

    @PostMapping("/contracts/{id}/settlement/{sellerId}")
    void settle(@PathVariable Long id, @PathVariable Long sellerId);
}
