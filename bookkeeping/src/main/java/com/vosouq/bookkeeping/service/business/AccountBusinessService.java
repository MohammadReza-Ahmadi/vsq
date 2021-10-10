package com.vosouq.bookkeeping.service.business;

import com.vosouq.bookkeeping.controller.dto.responses.AccountBalanceResponse;
import com.vosouq.bookkeeping.controller.dto.responses.AccountTurnoverResponse;
import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import com.vosouq.bookkeeping.model.journalizing.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountBusinessService {

    Account getOnlineUserAccount();

//    Account getOrCreateOnlineUserAccount();

    Account getOrCreateOnlineUserAccount(Long bookkeepingRequestId);

    Account getOrCreateOnlineUserAccount(Long userId,Long bookkeepingRequestId);

    void depositToAccountWithdrawableBalance(Account account, BigDecimal depositAmount);

    void blockAccountWithdrawableBalance(Account account, BigDecimal depositAmount);

    void withdrawFromAccountWithdrawableBalance(Account account, BigDecimal withdrawalAmount, Long bookkeepingRequestId);

    void withdrawFromAccountBlockedBalance(Account account, BigDecimal withdrawalAmount);

    void rechargeAccount(BigDecimal amount);

    void withdrawalAccountAndDepositToIban(String iban, BigDecimal amount);

    AccountBalanceResponse getWithdrawableBalance();

    List<AccountTurnoverResponse> getAccountTurnovers(AccountTransactionType transactionType);

    List<AccountTurnoverResponse> getAccountTurnovers(Long contractId);

    List<AccountTurnoverResponse> getAccountTurnovers(Long contractId,AccountTransactionType transactionType);
}
