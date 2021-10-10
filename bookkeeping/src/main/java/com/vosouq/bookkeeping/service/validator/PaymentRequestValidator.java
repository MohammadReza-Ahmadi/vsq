package com.vosouq.bookkeeping.service.validator;

import org.springframework.stereotype.Service;

@Service
public class PaymentRequestValidator {


    public boolean validateIpgPaymentResponse() {
        //this method take both parameters of: ipgResponse and PaymentRequest and check their equalities.
        return true;
    }

}
