package com.vosouq.paymentgateway.mellat.service.thirdparty;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value ="bookkeeping")
public interface BookkeepingRepository {

    /**
     * This is feign client method which calls paymentResponse() method of payment-bookkeeping's
     * ipg-response rest controller endpoint when reply of bpPayRequest is successful in psp center side.
     *
     * @param id is gatewayOrderId which is generate by payment-gateway when payment request was created.
     */
    @PutMapping(value = "ipg/payment-success")
    void paymentSuccess(@RequestParam Long id);

    @PutMapping(value = "ipg/payment-failed")
    void paymentFailed(@RequestParam Long id);

}
