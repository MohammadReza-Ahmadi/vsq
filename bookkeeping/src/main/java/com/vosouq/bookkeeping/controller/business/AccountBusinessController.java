package com.vosouq.bookkeeping.controller.business;

import com.vosouq.bookkeeping.controller.dto.requests.AccountWithdrawalRequest;
import com.vosouq.bookkeeping.controller.dto.responses.AccountBalanceResponse;
import com.vosouq.bookkeeping.controller.dto.responses.AccountTurnoverResponse;
import com.vosouq.bookkeeping.controller.dto.responses.ContractIpgPaymentResponse;
import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.service.business.AccountBusinessService;
import com.vosouq.bookkeeping.service.business.BookkeepingRequestBusinessService;
import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@VosouqRestController
@RequestMapping(value = "/accounts")
public class AccountBusinessController {


    private final AccountBusinessService accountBusinessService;
    private final BookkeepingRequestBusinessService bookkeepingRequestBusinessService;
    @Value("${payment.gateway.ipg.prefix}")
    private String pgwIpgEndpointPrefix;
    @Value("${payment.gateway.ipg.suffix}")
    private String pgwIpgEndpointSuffix;


    public AccountBusinessController(AccountBusinessService accountBusinessService, BookkeepingRequestBusinessService bookkeepingRequestBusinessService) {
        this.accountBusinessService = accountBusinessService;
        this.bookkeepingRequestBusinessService = bookkeepingRequestBusinessService;
    }


    @PutMapping(value = "/recharge/{amount}")
    public ContractIpgPaymentResponse ipgRecharge(@PathVariable BigDecimal amount) {
        /* create a bookkeeping request */
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestBusinessService.createAccountRechargeRequest(amount);
        /* create ContractIpgPaymentResponse by created url of hashed bookkeepingRequestId */
        return new ContractIpgPaymentResponse(pgwIpgEndpointPrefix + bookkeepingRequest.getGatewayOrderId() + pgwIpgEndpointSuffix);
    }

    @NoContent
    @PostMapping(value = "/withdrawal/")
    public void withdrawalAccountAndDepositToIban(@RequestBody AccountWithdrawalRequest request) {
        accountBusinessService.withdrawalAccountAndDepositToIban(request.getIban(),BigDecimal.valueOf(request.getAmount()));
    }


    /**
     * @return balance of current user' account
     */
    @GetMapping(value = "/balance")
    public AccountBalanceResponse getWithdrawableBalance() {
        return accountBusinessService.getWithdrawableBalance();
    }

    /**
     * @return list of turnovers of current user's account
     */
//    @GetMapping(value = {"/turnovers/", "/turnovers/{type}"})
    @GetMapping(value = {"/turnovers/{type}"})
    public List<AccountTurnoverResponse> getTurnoversOnAccount(@PathVariable(required = false) AccountTransactionType type) {
        return accountBusinessService.getAccountTurnovers(type);
    }

    /**
     * @param id is requesterId for bookkeeping service which actually is contract id in this controller
     * @return list of turnovers of current user's account
     */
    @GetMapping(value = {"/contract/{id}/turnovers"})
    public List<AccountTurnoverResponse> getTurnoversOnContract(@PathVariable Long id) {
        return accountBusinessService.getAccountTurnovers(id);
    }
}
