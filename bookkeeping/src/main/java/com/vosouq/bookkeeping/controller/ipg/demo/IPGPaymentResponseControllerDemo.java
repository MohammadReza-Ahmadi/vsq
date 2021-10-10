package com.vosouq.bookkeeping.controller.ipg.demo;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.bookkeeping.enumeration.GatewayType;
import com.vosouq.bookkeeping.enumeration.RequestStatus;
import com.vosouq.bookkeeping.service.business.BookkeepingRequestBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@VosouqRestController
@RequestMapping(value = "/ipg-callback-demo")
public class IPGPaymentResponseControllerDemo {

    private final GatewayType gatewayType = GatewayType.IPG;

    private final BookkeepingRequestBusinessService bookkeepingRequestBusinessService;

    public IPGPaymentResponseControllerDemo(BookkeepingRequestBusinessService bookkeepingRequestBusinessService) {
        this.bookkeepingRequestBusinessService = bookkeepingRequestBusinessService;
    }

    /**
     * This method called by payment-gateway microservice after done its job and
     * when callback method of payment-gateway is called by psp.
     */
    @PostMapping(value = "/pay-response")
    public void paymentResponse(@RequestParam Long gatewayOrderId, @RequestParam RequestStatus requestStatus) {
        log.info("gatewayOrderId:{} and paymentStatus:{}", gatewayOrderId, requestStatus);
        bookkeepingRequestBusinessService.createSuccessIpgPaymentRequest(gatewayOrderId);

        //these are gateway-payment microservice
        //1- load PaymentRequest by refId and update its status
        //2- if status=success then verity and then register journal and posting values
        //3- calling bpVerifyRequest endpoint of payment-gateway microservice
    }

}
