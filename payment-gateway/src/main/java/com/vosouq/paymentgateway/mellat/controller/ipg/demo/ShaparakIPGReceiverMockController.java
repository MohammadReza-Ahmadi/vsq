package com.vosouq.paymentgateway.mellat.controller.ipg.demo;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.paymentgateway.mellat.constant.IpgCallBackUrls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author <a href="mailto:m.reza79ahmadi@gmail.com">MohammadReza Ahmadi</a>
 * 22.08.20
 */

@Slf4j
@VosouqRestController
@RequestMapping(value = "/shaparak")
public class ShaparakIPGReceiverMockController {


    @PostMapping("/bp-pay-request")
    public ModelAndView bpPayRequest(@RequestParam(name = "RefId") String refId,
                                     @RequestParam(name = "mobileNo") String mobileNo
    ) {
        log.info("receive a bpPayRequest by refId: {} and mobileNo: {}", refId, mobileNo);
        ModelMap model = new ModelMap();
        model.addAttribute("RefId", refId);
        model.addAttribute("ResCode", "0");
        model.addAttribute("SaleOrderId", 10L);
        model.addAttribute("SaleReferenceId", 5142510L);
        model.addAttribute("FinalAmount", 480000L);
        model.addAttribute("CardHoldPAN", "610433****5689");
        model.addAttribute("CreditCardSaleResponseDetail", "00");
        String redirectUrl = IpgCallBackUrls.BP_PAY_RESPONSE_URL.split(IpgCallBackUrls.APP_URL_PORT)[1];
        log.info("redirect is: {}", redirectUrl);
        //todo: this redirection must be implement by post method
        return new ModelAndView("redirect:/" + redirectUrl, model);
    }
}
