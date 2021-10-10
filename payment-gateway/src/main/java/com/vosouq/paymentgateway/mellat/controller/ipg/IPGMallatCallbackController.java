package com.vosouq.paymentgateway.mellat.controller.ipg;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.paymentgateway.mellat.enumeration.BpResponseCode;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgCallbackParameters;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgPaymentResponse;
import com.vosouq.paymentgateway.mellat.service.builder.IpgCallbackParametersBuilder;
import com.vosouq.paymentgateway.mellat.service.ipg.business.IPGBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping(value = "/callbacks/ipg/mellat")
public class IPGMallatCallbackController {

    private IPGBusinessService IPGBusinessService;

    public IPGMallatCallbackController(IPGBusinessService IPGBusinessService) {
        this.IPGBusinessService = IPGBusinessService;
    }

    /**
     * This is callback method, which will be called by psp center in the response of bpPayRequest
     * that sent by refId parameter earlier. RefId, ResCode and SaleOrderId parameters should be equal to the
     * same parameters sent in the bpPayRequest. This checking is done in the handleBpPayResponse() method of
     * bpPayRequestBusinessService class.
     */
    @PostMapping
    public ModelAndView bpPayResponse(@RequestParam(value = "RefId", required = false) String refId,
                                      @RequestParam(value = "ResCode", required = false) String resCode,
                                      @RequestParam(value = "SaleOrderId", required = false) Long saleOrderId,
                                      @RequestParam(value = "SaleReferenceId", required = false) Long saleReferenceId,
                                      @RequestParam(value = "FinalAmount", required = false) Long finalAmount,
                                      @RequestParam(value = "CardHoldPAN", required = false) String cardHoldPAN,
                                      @RequestParam(value = "CreditCardSaleResponseDetail", required = false) String creditCardSaleResponseDetail
    ) {
        log.info("receive a bpPayResponse by refId: {}, resCode: {}, saleOrderId: {}, saleReferenceId: {}, finalAmount: {}, cardHoldPAN: {}, creditCardSaleResponseDetail: {}",
                refId, resCode, saleOrderId, saleReferenceId, finalAmount, cardHoldPAN, creditCardSaleResponseDetail);


        /* build IpgCallbackParameters instance */
        IpgCallbackParameters ipgCallbackParameters = IpgCallbackParametersBuilder.build(refId, resCode, saleOrderId, saleReferenceId, finalAmount, cardHoldPAN, creditCardSaleResponseDetail);

        /* handle ipg response */
        //todo: comment for test, should be uncommented
        IpgPaymentResponse ipgPaymentResponse = IPGBusinessService.handleIpgPaymentResponse(ipgCallbackParameters);
        Map<String, Object> params = new HashMap<>();
        params.put("contractId", ipgPaymentResponse.getRequesterId());

        //todo: just for test, should be removed.
//        IpgPaymentResponse ipgPaymentResponse = new IpgPaymentResponse(BpResponseCode.ERROR,123456L);
//        Map<String, Object> params = new HashMap<>();
//        params.put("contractId", ipgPaymentResponse.getRequesterId());
        //todo: end of test scope

        switch (ipgPaymentResponse.getBpResponseCode()) {
            case CANCEL:
                return new ModelAndView("ipg/mellat/mellat-ipg-cancel-response-intent-url", params);

            case SUCCESS:
                return new ModelAndView("ipg/mellat/mellat-ipg-success-response-intent-url", params);

            default:
                return new ModelAndView("ipg/mellat/mellat-ipg-error-response-intent-url", params);
        }
    }
}
