package com.vosouq.bookkeeping.controller.ipg.demo;

import com.vosouq.bookkeeping.controller.dto.requests.PayRequest;
import com.vosouq.bookkeeping.controller.dto.responses.PayResponse;
import com.vosouq.bookkeeping.enumeration.GatewayType;
import com.vosouq.bookkeeping.service.business.BookkeepingRequestBusinessService;
import com.vosouq.bookkeeping.service.crud.AccountCrudService;
import com.vosouq.commons.annotation.VosouqRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@VosouqRestController
@RequestMapping(value = "/ipg-demo")
public class IPGPaymentRequestControllerDemo {

    private final GatewayType gatewayType = GatewayType.IPG;

    private final BookkeepingRequestBusinessService bookkeepingRequestBusinessService;
    private final AccountCrudService accountCrudService;

    public IPGPaymentRequestControllerDemo(BookkeepingRequestBusinessService bookkeepingRequestBusinessService, AccountCrudService accountCrudService) {
        this.bookkeepingRequestBusinessService = bookkeepingRequestBusinessService;
        this.accountCrudService = accountCrudService;
    }

    @PostMapping(value = "/pay-request")
    public ModelAndView paymentRequest(@ModelAttribute("payRequest") PayRequest payRequest) {

        /** first save user account*/
        PayResponse payResponse = bookkeepingRequestBusinessService.createBookkeepingRequest_DEMO(payRequest, gatewayType);
        Map<String, Object> params = new HashMap<>();
        params.put("payResponse", payResponse);
        return new ModelAndView("demo/contract-payment-response-demo", params);
    }

    /** render payment demo page */
    @GetMapping(value = "/show-contract-payment-demo-page")
    public ModelAndView showContractPaymentDemoPage() {
        /** load Vosouq and User account sample and sent to Page*/
        Map<String, Object> params = new HashMap<>();
        params.put("payerId",1);
        params.put("recipientId", 2);

        return new ModelAndView("demo/contract-payment-request-demo",params);
    }

    @PostMapping(value = "/mellat-payment-gateway")
    public ModelAndView showMellatPaymentGatewayRequestPage(@RequestParam String RefId, @RequestParam String mobileNo) {
        Map<String, Object> param = new HashMap<>();
        param.put("RefId",RefId);
        param.put("mobileNo",mobileNo);
        return new ModelAndView("demo/mellat-payment-gateway-page-demo", param);
    }

    /** this is mock method to simulate payment action in mellat gateway payment*/
    @PostMapping(value = "/pay-request-confirm")
    public ModelAndView showMellatPaymentGatewayResponsePage(@RequestParam String refId) {
        Map<String, Object> param = new HashMap<>();
        param.put("RefId",refId);
        return new ModelAndView("demo/mellat-payment-gateway-response-page-demo", param);
    }
}
