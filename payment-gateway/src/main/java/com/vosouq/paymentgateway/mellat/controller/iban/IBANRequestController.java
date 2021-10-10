package com.vosouq.paymentgateway.mellat.controller.iban;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.paymentgateway.mellat.controller.dto.IBANPaymentRequest;
import com.vosouq.paymentgateway.mellat.service.iban.business.IBANBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Slf4j
@VosouqRestController
@RequestMapping(value = "/iban")
public class IBANRequestController {

    private final IBANBusinessService ibanBusinessService;

    public IBANRequestController(IBANBusinessService ibanBusinessService) {
        this.ibanBusinessService = ibanBusinessService;
    }

    @PostMapping(value = "/pay-request")
    public String bpPayRequest(@RequestBody IBANPaymentRequest ibanPaymentRequest) {
        log.info("receive a {}", ibanPaymentRequest);
        ibanBusinessService.paymentRequest(ibanPaymentRequest.getIban(), ibanPaymentRequest.getAmount());
        return "RefId";
    }

}