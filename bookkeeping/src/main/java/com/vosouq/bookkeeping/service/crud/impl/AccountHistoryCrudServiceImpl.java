//package com.vosouq.bookkeeping.service.audit.impl;
//
//import com.vosouq.bookkeeping.model.AccountHistory;
//import com.vosouq.bookkeeping.repository.AccountHistoryRepository;
//import com.vosouq.bookkeeping.service.AccountHistoryCrudService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AccountHistoryCrudServiceImpl implements AccountHistoryCrudService {
//
//    private final AccountHistoryRepository accountHistoryRepository;
//
//    public AccountHistoryCrudServiceImpl(AccountHistoryRepository accountHistoryRepository) {
//        this.accountHistoryRepository = accountHistoryRepository;
//    }
//
//    @Override
//    public AccountHistory save(AccountHistory accountHistory) {
//        return accountHistoryRepository.save(accountHistory);
//    }
//}
