package com.vosouq.bookkeeping.service.audit.impl;

import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.repository.AccountRepository;
import com.vosouq.bookkeeping.service.audit.AccountAuditService;
import com.vosouq.bookkeeping.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccountAuditServiceImpl implements AccountAuditService {

    private final AccountRepository accountRepository;
    private final EntityManager entityManager;

    public AccountAuditServiceImpl(AccountRepository accountRepository, EntityManager entityManager) {
        this.accountRepository = accountRepository;
        this.entityManager = entityManager;
    }

    @Override
    public List<Account> getRevisionsById(Long accountId, boolean selectDeletedEntities) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(Account.class, true, selectDeletedEntities);
        q.add(AuditEntity.id().eq(accountId));
        List<Account> resultList = q.getResultList();
        if (AppUtil.isListEmpty(resultList))
            return new ArrayList<>();
        return resultList;
    }

    @Override
    public List<Account> getRevisionsByIdAndTransactionType(Long accountId, AccountTransactionType transactionType) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(Account.class, true, true);
        q.add(AuditEntity.id().eq(accountId));
        q.add(AuditEntity.property(Account.FLD_transactionType).ne(AccountTransactionType.BLOCK));
        q.add(AuditEntity.property(Account.FLD_transactionType).ne(AccountTransactionType.UNBLOCK));
        if (AppUtil.isNotNull(transactionType)) {
            q.add(AuditEntity.property(Account.FLD_transactionType).eq(transactionType));
        }
        q.addOrder(AuditEntity.revisionNumber().desc());
        List<Account> resultList = q.getResultList();
        if (AppUtil.isListEmpty(resultList))
            return new ArrayList<>();
        return resultList;
    }

    @Override
    public List<Account> getRevisionsByIdAndBookkeepingRequestId(Long accountId, AccountTransactionType transactionType, List<Long> bookkeepingRequestIds) {
        return getRevisionsByIdAndBookkeepingRequestIdAndTransactionType(accountId, bookkeepingRequestIds, null);
    }

    @Override
    public List<Account> getRevisionsByIdAndBookkeepingRequestIdAndTransactionType(Long accountId, List<Long> bookkeepingRequestIds, AccountTransactionType transactionType) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);
        AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(Account.class, true, true);
        q.add(AuditEntity.id().eq(accountId));
        q.add(AuditEntity.property(Account.FLD_bookkeepingRequestId).in(bookkeepingRequestIds));
        q.add(AuditEntity.property(Account.FLD_transactionType).ne(AccountTransactionType.BLOCK));
        q.add(AuditEntity.property(Account.FLD_transactionType).ne(AccountTransactionType.UNBLOCK));
        if (AppUtil.isNotNull(transactionType)) {
            q.add(AuditEntity.property(Account.FLD_transactionType).eq(transactionType));
        }
        q.addOrder(AuditEntity.revisionNumber().asc());
        List<Account> resultList = q.getResultList();
        if (AppUtil.isListEmpty(resultList))
            return new ArrayList<>();
        return resultList;
    }
}
