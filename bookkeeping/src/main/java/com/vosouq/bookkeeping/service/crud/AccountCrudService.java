package com.vosouq.bookkeeping.service.crud;

import com.vosouq.bookkeeping.model.journalizing.Account;

public interface AccountCrudService {

    Account save(Account account);

    Account createUserRialAccount(Long userId);

    Account createUserRialAccount(Long userId, Long bookkeepingRequestId);

    Account findById(Long accountId);

    Account findByUserId(Long userId);

    Account findByVosouqId();
}
