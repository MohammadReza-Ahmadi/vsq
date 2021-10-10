package com.vosouq.bookkeeping.controller.ipg;

import com.vosouq.bookkeeping.service.business.BookkeepingRequestBusinessService;
import com.vosouq.commons.annotation.VosouqRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@VosouqRestController
@RequestMapping(value = "/ipg")
public class IpgMellatPGResponseController {

    private final BookkeepingRequestBusinessService bookkeepingRequestBusinessService;

    public IpgMellatPGResponseController(BookkeepingRequestBusinessService bookkeepingRequestBusinessService) {
        this.bookkeepingRequestBusinessService = bookkeepingRequestBusinessService;
    }

    /**
     * @param id is gatewayOrderId
     */
    @PutMapping(value = "/payment-success")
    public void paymentSuccess(@RequestParam Long id) {
        log.info("call paymentSuccess() by gatewayOrderId id:{}", id);
        bookkeepingRequestBusinessService.createSuccessIpgPaymentRequest(id);
    }

    /**
     * @param id is gatewayOrderId
     */
    @PutMapping(value = "/payment-failed")
    public void paymentFailed(@RequestParam Long id) {
        log.info("call paymentFailed() by gatewayOrderId id:{}", id);
        bookkeepingRequestBusinessService.createFailedIpgPaymentRequest(id);
    }
}
