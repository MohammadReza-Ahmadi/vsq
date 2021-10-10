package com.vosouq.bookkeeping.service.business.thirdparty;

import com.vosouq.bookkeeping.controller.dto.responses.IpgResponse;
import com.vosouq.bookkeeping.controller.dto.requests.GetIpgPaymentRefIdRequest;
import com.vosouq.bookkeeping.controller.dto.requests.IBANPaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * This is PaymentGatewayService which its responsibility is calling payment-gateway endpoints for
 * sending its payment requests and give back reference id.
 * This class works by the help of feign mechanism.
 */

@FeignClient(value = "payment-gateway")
public interface PaymentGatewayRepository {


    @PostMapping(value = "/ipg/mellat/ref-id")
    IpgResponse getIpgPaymentRequestRefId(@RequestBody GetIpgPaymentRefIdRequest request);

    /**
     * IBAN payment request service
     * @param ibanPaymentRequest is dto object of iban payment request data
     * @return String value of payment refId
     */
    @PostMapping(value = "/iban/pay-request")
    String ibanPayRequest(@RequestBody IBANPaymentRequest ibanPaymentRequest);

}
