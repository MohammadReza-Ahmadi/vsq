package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.domainobject.VoucherFormulaAmount;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.service.business.AccountBusinessService;
import com.vosouq.bookkeeping.service.business.BookkeepingBusinessService;
import com.vosouq.bookkeeping.service.business.BookkeepingRequestBusinessService;
import com.vosouq.bookkeeping.service.business.ContractBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ContractBusinessServiceImpl implements ContractBusinessService {

    private final BookkeepingRequestBusinessService bookkeepingRequestBusinessService;
    private final BookkeepingBusinessService bookkeepingBusinessService;
    private final AccountBusinessService accountBusinessService;

    public ContractBusinessServiceImpl(BookkeepingRequestBusinessService bookkeepingRequestBusinessService, BookkeepingBusinessService bookkeepingBusinessService, AccountBusinessService accountBusinessService) {
        this.bookkeepingRequestBusinessService = bookkeepingRequestBusinessService;
        this.bookkeepingBusinessService = bookkeepingBusinessService;
        this.accountBusinessService = accountBusinessService;
    }

    @Override
    public void create(Long contractId) {
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestBusinessService.createContractRegistrationRequest(contractId);
        bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.CONTRACT_CREATE);
    }

    @Override
    public void goodsDelivery(Long contractId) {
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestBusinessService.createContractGoodsDeliveryRequest(contractId);
        bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.CONTRACT_GOODS_DELIVERY);
    }

    @Override
    public void settlement(Long contractId, Long sellerUserId) {
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestBusinessService.createSettlementContractRequest(contractId, sellerUserId);
        VoucherFormulaAmount voucherFormulaAmount = bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.CONTRACT_SETTLEMENT);

        /* buyer's account*/
        Account creditAccount = bookkeepingRequest.getCreditAccount();
        /* withdraw whole contract amount from blockedBalance of buyer's account */
        accountBusinessService.withdrawFromAccountBlockedBalance(creditAccount, voucherFormulaAmount.getAmount());

        /* seller's account*/
        Account debitAccount = bookkeepingRequest.getDebitAccount();
        /* deposit whole contract amount to withdrawable balance of seller's account */
        accountBusinessService.depositToAccountWithdrawableBalance(debitAccount, voucherFormulaAmount.getAmount());
        /* withdraw commission amount from withdrawable balance of seller's account */
        accountBusinessService.withdrawFromAccountWithdrawableBalance(debitAccount, voucherFormulaAmount.getCommissionAmount(), bookkeepingRequest.getId());
        /* withdraw vat amount from withdrawable balance of seller's account */
        accountBusinessService.withdrawFromAccountWithdrawableBalance(debitAccount, voucherFormulaAmount.getVATAmount(), bookkeepingRequest.getId());
    }
}
