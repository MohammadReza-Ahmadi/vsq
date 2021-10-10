package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import com.vosouq.bookkeeping.repository.JournalEntryRepository;
import com.vosouq.bookkeeping.repository.BookkeepingRequestRepository;
import com.vosouq.bookkeeping.service.crud.AccountCrudService;
import com.vosouq.bookkeeping.service.crud.JournalCrudService;
import com.vosouq.bookkeeping.service.crud.BookkeepingRequestCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JournalCrudServiceImpl implements JournalCrudService {

    private final JournalEntryRepository journalEntryRepository;
    private final BookkeepingRequestCrudService bookkeepingRequestCrudService;
    private final AccountCrudService accountCrudService;

    public JournalCrudServiceImpl(JournalEntryRepository journalEntryRepository, BookkeepingRequestRepository paymentRequestService, BookkeepingRequestCrudService bookkeepingRequestCrudService1, AccountCrudService accountCrudService) {
        this.journalEntryRepository = journalEntryRepository;
        this.bookkeepingRequestCrudService = bookkeepingRequestCrudService1;
        this.accountCrudService = accountCrudService;
    }

    //    @PostConstruct
    private void init() {
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestCrudService.getAllBookkeepingRequestsByRequesterId(123L).get(0);
        SubsidiaryLedger subsidiaryLedger = new SubsidiaryLedger();
//        journal.setDebitAccount(accountService.findByUserId(1000L));
//        journal.setCreditAccount(accountService.findByUserId(1L));
        subsidiaryLedger.setDebit(bookkeepingRequest.getAmount());
        subsidiaryLedger.setCredit(bookkeepingRequest.getAmount());
        subsidiaryLedger.setDescription(bookkeepingRequest.getDescription());
        subsidiaryLedger.setDateTime(new Date());
//        subLedger.setTransactionStatus(TransactionStatus.SUCCESS);
//        subLedger.set(bookkeepingRequest);
//        bookkeepingRequest.setSubsidiaryLedger(subsidiaryLedger);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
    }

    @Override
    public JournalEntry save(JournalEntry journalEntry) {
        log.info("saving Journal: {}", journalEntry);
        return journalEntryRepository.save(journalEntry);
    }
}
