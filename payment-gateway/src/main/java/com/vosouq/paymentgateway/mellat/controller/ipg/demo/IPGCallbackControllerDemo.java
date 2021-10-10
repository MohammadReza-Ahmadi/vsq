package com.vosouq.paymentgateway.mellat.controller.ipg.demo;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgCallbackParameters;
import com.vosouq.paymentgateway.mellat.service.builder.IpgCallbackParametersBuilder;
import com.vosouq.paymentgateway.mellat.service.ipg.business.IPGBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:m.reza79ahmadi@gmail.com">MohammadReza Ahmadi</a>
 * 19.08.20
 * This is Internet Payment Gateway (IPG) callback handler rest controller (callback method of IPG)
 * which will be called by psp center to response every bpPayRequest. But responsiveness of this class
 * will increase by adding bpVerify and bpSettle methods.
 */

@Slf4j
@VosouqRestController
@RequestMapping(value = "/ipg-callback-demo")
public class IPGCallbackControllerDemo {

    private IPGBusinessService IPGBusinessService;

    public IPGCallbackControllerDemo(IPGBusinessService IPGBusinessService) {
        this.IPGBusinessService = IPGBusinessService;
    }

    @GetMapping(value = "/{contractId}")
    public ModelAndView bpPayResponse(@PathVariable Long contractId) {
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", contractId);
        return new ModelAndView("mellat-payment-response-intent-url-demo",params);
    }

    /**
     * This is callback method, which will be called by psp center in the response of bpPayRequest
     * that sent by refId parameter earlier. RefId, ResCode and SaleOrderId parameters should be equal to the
     * same parameters sent in the bpPayRequest. This checking is done in the handleBpPayResponse() method of
     * bpPayRequestBusinessService class.
     * */
    @PostMapping("/bp-pay-response")
    public ModelAndView bpPayResponse(@RequestParam("RefId") String refId,
                                      @RequestParam("ResCode") String resCode,
                                      @RequestParam("SaleOrderId") Long saleOrderId,
                                      @RequestParam("SaleReferenceId") Long saleReferenceId,
                                      @RequestParam("FinalAmount") Long finalAmount,
                                      @RequestParam("CardHoldPAN") String cardHoldPAN,
                                      @RequestParam("CreditCardSaleResponseDetail") String creditCardSaleResponseDetail
    ) {
        log.info("receive a bpPayResponse by refId: {}, resCode: {}, saleOrderId: {}, saleReferenceId: {}, finalAmount: {}, cardHoldPAN: {}, creditCardSaleResponseDetail: {}",
                refId, resCode, saleOrderId, saleReferenceId, finalAmount, cardHoldPAN, creditCardSaleResponseDetail);

        /* build IpgCallbackParameters instance */
        IpgCallbackParameters ipgCallbackParameters = IpgCallbackParametersBuilder.build(refId, resCode, saleOrderId, saleReferenceId, finalAmount, cardHoldPAN, creditCardSaleResponseDetail);

        /* handle ipg response */
        IPGBusinessService.handleIpgPaymentResponse(ipgCallbackParameters);

        IPGBusinessService.handleIpgPaymentResponse(ipgCallbackParameters);
        return new ModelAndView("demo/bp-pay-response");
    }
}
