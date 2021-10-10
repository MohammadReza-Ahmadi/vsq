package com.vosouq.bookkeeping.controller.business;


import com.vosouq.bookkeeping.controller.dto.responses.ContractIpgPaymentResponse;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.service.business.BookkeepingRequestBusinessService;
import com.vosouq.bookkeeping.service.business.ContractBusinessService;
import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@VosouqRestController
@RequestMapping(value = "/contracts")
public class ContractBusinessController {

    private final ContractBusinessService contractBusinessService;
    private final BookkeepingRequestBusinessService bookkeepingRequestBusinessService;
    @Value("${payment.gateway.ipg.prefix}")
    private String pgwIpgEndpointPrefix;
    @Value("${payment.gateway.ipg.suffix}")
    private String pgwIpgEndpointSuffix;

    public ContractBusinessController(ContractBusinessService contractBusinessService, BookkeepingRequestBusinessService bookkeepingRequestBusinessService) {
        this.contractBusinessService = contractBusinessService;
        this.bookkeepingRequestBusinessService = bookkeepingRequestBusinessService;
    }

    /**
     * @param id is requesterId for bookkeeping service which actually is contract id in this controller
     */
    @NoContent
    @PostMapping(value = "/{id}/create")
    public void create(@PathVariable Long id) {
        contractBusinessService.create(id);
    }

    /**
     * @param id is requesterId for bookkeeping service which actually is contract id in this controller
     * @return the url which is the payment-gateway microservice payment's endpoint url
     */
    @Created
    @PostMapping(value = "{id}/ipg/payment")
    public ContractIpgPaymentResponse ipgPaymentRequest(@PathVariable Long id) {
        /* create a bookkeeping request */
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestBusinessService.createContractIpgPaymentRequest(id);
        /* create ContractIpgPaymentResponse by created url of hashed bookkeepingRequestId */
        return new ContractIpgPaymentResponse(pgwIpgEndpointPrefix + bookkeepingRequest.getGatewayOrderId() + pgwIpgEndpointSuffix);
    }

    /**
     * @param id is requesterId for bookkeeping service which actually is contract id in this controller
     */
    @PutMapping(value = "{id}/account/payment")
    public void accountPayment(@PathVariable Long id) {
        /* create a bookkeeping request */
       bookkeepingRequestBusinessService.createContractAccountPaymentRequest(id);
    }

    /**
     * @param id is requesterId for bookkeeping service which actually is contract id in this controller
     */
    @PutMapping(value = "/{id}/delivery")
    public void goodsDelivery(@PathVariable Long id) {
        contractBusinessService.goodsDelivery(id);
    }

    /**
     * @param id is requesterId for bookkeeping service which actually is contract id in this controller
     * @param sellerId is seller user id
     */
    @NoContent
    @PostMapping(value = "/{id}/settlement/{sellerId}")
    public void settlement(@PathVariable Long id, @PathVariable Long sellerId) {
        contractBusinessService.settlement(id, sellerId);
    }
}
