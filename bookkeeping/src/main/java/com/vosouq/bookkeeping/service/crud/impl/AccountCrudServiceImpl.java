package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.enumeration.AccountStatus;
import com.vosouq.bookkeeping.enumeration.AccountType;
import com.vosouq.bookkeeping.enumeration.CurrencyCode;
import com.vosouq.bookkeeping.enumeration.SubsidiaryLegerAccountCategory;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import com.vosouq.bookkeeping.repository.AccountRepository;
import com.vosouq.bookkeeping.service.crud.AccountCrudService;
import com.vosouq.bookkeeping.service.crud.CurrencyCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class AccountCrudServiceImpl implements AccountCrudService {

    private final AccountRepository accountRepository;
    private final CurrencyCrudService currencyCrudService;
    @Value("${bookkeeping.account.title}")
    private String userAccountTitle;
    @Value("${bookkeeping.account.desc}")
    private String userAccountDesc;

    public AccountCrudServiceImpl(AccountRepository accountRepository, CurrencyCrudService currencyCrudService) {
        this.accountRepository = accountRepository;
        this.currencyCrudService = currencyCrudService;
    }


    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account createUserRialAccount(Long userId) {
        return createUserRialAccount(userId,null);
    }

    @Override
    public Account createUserRialAccount(Long userId, Long bookkeepingRequestId) {
        Account userAccount = new Account();
        userAccount.setUserId(userId);
        userAccount.setBookkeepingRequestId(bookkeepingRequestId);
        userAccount.setType(AccountType.USER);
        userAccount.setSubsidiaryLedger(new SubsidiaryLedger(SubsidiaryLegerAccountCategory.EXCHANGE_TRADABLE_ACCOUNT_OF_CUSTOMERS.getCode()));
        userAccount.setCurrency(currencyCrudService.findByCode(CurrencyCode.IRR));
        userAccount.setStatus(AccountStatus.ACTIVE);
        userAccount.setWithdrawableBalance(BigDecimal.ZERO);
        userAccount.setBlockedBalance(BigDecimal.ZERO);
        userAccount.setBalance(BigDecimal.ZERO);
        userAccount.setTransactionAmount(BigDecimal.ZERO);
        userAccount.setTitle("حساب اعتباری کاربر:" + userId);
        userAccount.setDescription("حساب اعتباری کاربر زیر سرفصل معین:موجودی قابل معامله مشتریان");
        Date createDate = new Date();
        userAccount.setCreateTimestamp(createDate);
        userAccount.setCreateTimestamp(createDate);
        return save(userAccount);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    public Account findByVosouqId() {
        /* todo: VosouqUserId should be get from session */
        Long vosouqUserId = 1L;
        return accountRepository.findByUserId(vosouqUserId);
    }
}
