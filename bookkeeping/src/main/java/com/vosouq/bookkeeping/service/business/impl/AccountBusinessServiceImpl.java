package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.constant.NumberConstants;
import com.vosouq.bookkeeping.controller.dto.responses.AccountBalanceResponse;
import com.vosouq.bookkeeping.controller.dto.responses.AccountTurnoverResponse;
import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.service.audit.AccountAuditService;
import com.vosouq.bookkeeping.service.business.AccountBusinessService;
import com.vosouq.bookkeeping.service.business.BookkeepingBusinessService;
import com.vosouq.bookkeeping.service.business.BookkeepingRequestBusinessService;
import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.bookkeeping.service.crud.AccountCrudService;
import com.vosouq.bookkeeping.service.crud.BookkeepingRequestCrudService;
import com.vosouq.bookkeeping.service.crud.IbanAccountCrudService;
import com.vosouq.bookkeeping.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountBusinessServiceImpl implements AccountBusinessService {

    private final BookkeepingRequestBusinessService bookkeepingRequestBusinessService;
    private final BookkeepingBusinessService bookkeepingBusinessService;
    private final BookkeepingRequestCrudService bookkeepingRequestCrudService;
    private final AccountCrudService accountCrudService;
    private final AccountAuditService accountAuditService;
    private final UserBusinessService userBusinessService;
    private final IbanAccountCrudService ibanAccountCrudService;


    public AccountBusinessServiceImpl(@Lazy BookkeepingRequestBusinessService bookkeepingRequestBusinessService, BookkeepingBusinessService bookkeepingBusinessService, BookkeepingRequestCrudService bookkeepingRequestCrudService, AccountCrudService accountCrudService, AccountAuditService accountAuditService, UserBusinessService userBusinessService1, IbanAccountCrudService ibanAccountCrudService) {
        this.bookkeepingRequestBusinessService = bookkeepingRequestBusinessService;
        this.bookkeepingBusinessService = bookkeepingBusinessService;
        this.bookkeepingRequestCrudService = bookkeepingRequestCrudService;
        this.accountCrudService = accountCrudService;
        this.accountAuditService = accountAuditService;
        this.userBusinessService = userBusinessService1;
        this.ibanAccountCrudService = ibanAccountCrudService;
    }


    @Override
    public Account getOnlineUserAccount() {
        Long userId = userBusinessService.getCurrentUserId();
        return accountCrudService.findByUserId(userId);
    }

    @Override
    public Account getOrCreateOnlineUserAccount(Long bookkeepingRequestId) {
        Long userId = userBusinessService.getCurrentUserId();
        return getOrCreateOnlineUserAccount(userId, bookkeepingRequestId);
    }

    @Override
    public Account getOrCreateOnlineUserAccount(Long userId, Long bookkeepingRequestId) {
        Account account = accountCrudService.findByUserId(userId);
        /* create account for online user if there is no account for the online user */
        if (AppUtil.isNull(account)) {
            account = accountCrudService.createUserRialAccount(userId, bookkeepingRequestId);
        } else {
            account.setBookkeepingRequestId(bookkeepingRequestId);
            account.setTransactionType(null);
            account.setTransactionAmount(BigDecimal.ZERO);
            account.setUpdateTimestamp(new Date());
        }
        return account;
    }

    /**
     * Increase the balance of account
     *
     * @param account       specified account instance
     * @param depositAmount is requested amount for increasing account's balance
     */
    @Override
    public void depositToAccountWithdrawableBalance(Account account, BigDecimal depositAmount) {
        account.increaseBalance(depositAmount);
        account.increaseWithdrawableBalance(depositAmount);
        account.setTransactionAmount(depositAmount);
        account.setTransactionType(AccountTransactionType.DEPOSIT);
        account.setUpdateTimestamp(new Date());
        accountCrudService.save(account);
        log.debug("The withdrawable balance of account with id:[{}] is increased by amount:[{}].", account.getId(), depositAmount);
    }

    @Override
    public void blockAccountWithdrawableBalance(Account account, BigDecimal blockedAmount) {
        account.increaseBlockedBalance(blockedAmount);
        account.setTransactionAmount(blockedAmount);
        account.setTransactionType(AccountTransactionType.BLOCK);
        account.setUpdateTimestamp(new Date());
        accountCrudService.save(account);
        log.debug("The blocked balance of account with id:[{}] is increased by amount:[{}].", account.getId(), blockedAmount);
    }

    @Override
    public void withdrawFromAccountWithdrawableBalance(Account account, BigDecimal withdrawalAmount, Long bookkeepingRequestId) {
        account.setBookkeepingRequestId(bookkeepingRequestId);
        account.decreaseBalance(withdrawalAmount);
        account.decreaseWithdrawableBalance(withdrawalAmount);
        account.setTransactionAmount(withdrawalAmount);
        account.setTransactionType(AccountTransactionType.WITHDRAWAL);
        account.setUpdateTimestamp(new Date());
        accountCrudService.save(account);
        log.debug("The withdrawable balance of account with id:[{}] is decreased by amount:[{}].", account.getId(), withdrawalAmount);
    }

    @Override
    public void withdrawFromAccountBlockedBalance(Account account, BigDecimal withdrawalAmount) {
        account.decreaseBlockedBalance(withdrawalAmount);
        account.setTransactionAmount(withdrawalAmount);
        account.setTransactionType(AccountTransactionType.UNBLOCK);
        account.setUpdateTimestamp(new Date());
        accountCrudService.save(account);
        log.debug("The blocked balance of account with id:[{}] is decreased by amount:[{}].", account.getId(), withdrawalAmount);
    }


    /**
     * Increase the balance of account by specified amount and create related vouchers
     *
     * @param amount is requested amount for decreasing account's balance
     */
    @Override
    public void rechargeAccount(BigDecimal amount) {
        /* create bookkeeping request */
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestBusinessService.createAccountRechargeRequest(amount);
        /* increase account's balance */
        depositToAccountWithdrawableBalance(bookkeepingRequest.getCreditAccount(), bookkeepingRequest.getAmount());
        /* create voucher*/
        bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.ACCOUNT_RECHARGE);
    }

    @Override
    public void withdrawalAccountAndDepositToIban(String iban, BigDecimal amount) {
        /*find user's iban info from IbanAccount table*/
        ibanAccountCrudService.getByIban(iban);

        /*call iban webservice and handle related services */
        //todo: call payment-gateway and get new RefId for iban transaction.
        //todo: this scope should be completed after clarifying iban mechanism
        //todo: for this part assume that iban transaction is done successfully and continue rest of operation

        /*create bookkeeping request*/
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestBusinessService.createAccountWithdrawalRequest(iban, amount);
        /* withdraw from user's account */
        withdrawFromAccountWithdrawableBalance(bookkeepingRequest.getDebitAccount(), bookkeepingRequest.getAmount(), bookkeepingRequest.getId());
        /* create voucher*/
        bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.ACCOUNT_WITHDRAWAL);
    }

    @Override
    public AccountBalanceResponse getWithdrawableBalance() {
        Account userAccount = getOnlineUserAccount();
        if (AppUtil.isNull(userAccount))
            return new AccountBalanceResponse(NumberConstants.ZERO_LONG);
        return new AccountBalanceResponse(userAccount.getWithdrawableBalance().longValue());
    }

    @Override
    public List<AccountTurnoverResponse> getAccountTurnovers(AccountTransactionType transactionType) {
        Account userAccount = getOnlineUserAccount();
        if (AppUtil.isNull(userAccount))
            return AppUtil.getEmptyList();

        List<Account> accountAudits = accountAuditService.getRevisionsByIdAndTransactionType(userAccount.getId(), transactionType);
        return accountAudits.stream().filter(Objects::nonNull)
                .map(audit -> new AccountTurnoverResponse(audit.getTransactionType(), audit.getTransactionAmount().longValue(), audit.getUpdateTimestamp()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountTurnoverResponse> getAccountTurnovers(Long contractId) {
        return getAccountTurnovers(contractId, null);
    }

    @Override
    public List<AccountTurnoverResponse> getAccountTurnovers(Long contractId, AccountTransactionType transactionType) {
        Account account = getOnlineUserAccount();
        if (AppUtil.isNull(account))
            return AppUtil.getEmptyList();

        /* get all bookkeepingRequest of this requesterId */
        List<BookkeepingRequest> bookkeepingRequestList = bookkeepingRequestCrudService.getAllBookkeepingRequestsByRequesterId(contractId);
        /* extract requestIdList */
        List<Long> bookkeepingRequestIds = bookkeepingRequestList.stream().map(BookkeepingRequest::getId).collect(Collectors.toList());
        /* call auditService to find all account audits of related bookkeepingRequestIds*/
        List<Account> accountAudits = accountAuditService.getRevisionsByIdAndBookkeepingRequestIdAndTransactionType(account.getId(), bookkeepingRequestIds, transactionType);
        return accountAudits.stream().filter(Objects::nonNull)
                .map(audit -> new AccountTurnoverResponse(audit.getTransactionType(), audit.getTransactionAmount().longValue(), audit.getUpdateTimestamp()))
                .collect(Collectors.toList());
    }
}
