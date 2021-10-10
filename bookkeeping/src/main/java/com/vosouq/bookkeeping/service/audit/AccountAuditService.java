package com.vosouq.bookkeeping.service.audit;

import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import com.vosouq.bookkeeping.model.journalizing.Account;

import java.util.List;

public interface AccountAuditService {

    List<Account> getRevisionsById(Long accountId, boolean selectDeletedEntities);

    List<Account> getRevisionsByIdAndTransactionType(Long accountId, AccountTransactionType transactionType);

    List<Account> getRevisionsByIdAndBookkeepingRequestId(Long accountId, AccountTransactionType transactionType, List<Long> bookkeepingRequestIds);

    List<Account> getRevisionsByIdAndBookkeepingRequestIdAndTransactionType(Long accountId, List<Long> bookkeepingRequestIds, AccountTransactionType transactionType);
}
