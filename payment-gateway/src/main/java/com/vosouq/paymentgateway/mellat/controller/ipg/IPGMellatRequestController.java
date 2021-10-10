package com.vosouq.paymentgateway.mellat.controller.ipg;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.paymentgateway.mellat.controller.dto.GetIpgPaymentRefIdRequest;
import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.OrderSequence;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgResponse;
import com.vosouq.paymentgateway.mellat.repository.BpPaymentRepository;
import com.vosouq.paymentgateway.mellat.service.ipg.business.IPGBusinessService;
import com.vosouq.paymentgateway.mellat.service.thirdparty.BookkeepingRepository;
import com.vosouq.paymentgateway.mellat.webservice.ipg.IPGSoapConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * This is IPG rest controller class that its responsibility is acting as an endpoint for
 * all payment requests by the means of In   private Long orderId;ternet payment Gateway (IPG) will be handled in this endpoint.
 * The main requester of this rest controller is payment-bookkeeping microservice.
 */


@Slf4j
@VosouqRestController
@RequestMapping(value = "/ipg/mellat")
public class IPGMellatRequestController {

    private final IPGBusinessService ipgBusinessService;
    private final BpPaymentRepository bpPaymentRepository;
    private final BookkeepingRepository bookkeepingRepository;
    private final IPGSoapConnector ipgSoapConnector;

    public IPGMellatRequestController(IPGBusinessService ipgBusinessService, BpPaymentRepository bpPaymentRepository, BookkeepingRepository bookkeepingRepository, IPGSoapConnector ipgSoapConnector) {
        this.ipgBusinessService = ipgBusinessService;
        this.bpPaymentRepository = bpPaymentRepository;
        this.bookkeepingRepository = bookkeepingRepository;
        this.ipgSoapConnector = ipgSoapConnector;
    }

    @PostMapping(value = "/ref-id")
    IpgResponse getIpgPaymentRefId(@RequestBody GetIpgPaymentRefIdRequest request) {
        /* create BpPaymentResponse after creating BpPayRequest instance and saving BpPayment entity */
        return ipgBusinessService.getIpgPaymentRefId(request);
    }

    /**
     * @param id orderId
     * @return
     */
    @GetMapping(value = "/{id}/payment")
    public ModelAndView paymentRequestRedirection(@PathVariable Long id) {
        BpPayment bpPayment = bpPaymentRepository.findByOrderSequence(new OrderSequence(id));
        /* redirect to mellat psp payment page by posting refId and mobileNo */
        Map<String, Object> params = new HashMap<>();
        params.put("refId", bpPayment.getRefId());
//        params.put("mobileNo", ipgGetRefIdResponse.getMobileNo());
        return new ModelAndView("ipg/mellat/mellat-ipg-payment-page-redirection", params);
    }
}
