package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.domainobject.VoucherFormulaAmount;
import com.vosouq.bookkeeping.model.journalizing.GeneralLedger;
import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.model.posting.PostingModel;
import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.service.business.*;
import com.vosouq.bookkeeping.service.crud.GeneralLedgerCrudService;
import com.vosouq.bookkeeping.service.crud.JournalEntryCrudService;
import com.vosouq.bookkeeping.service.crud.SubsidiaryLedgerCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BookkeepingBusinessServiceImpl implements BookkeepingBusinessService {

    private final PostingModelBusinessService postingModelBusinessService;
    private final JournalEntryBusinessService journalEntryBusinessService;
    private final SubsidiaryLedgerBusinessService subsidiaryLedgerBusinessService;
    private final GeneralLedgerBusinessService generalLedgerBusinessService;
    private final JournalEntryCrudService journalEntryCrudService;
    private final SubsidiaryLedgerCrudService subsidiaryLedgerCrudService;
    private final GeneralLedgerCrudService generalLedgerCrudService;


    public BookkeepingBusinessServiceImpl(PostingModelBusinessService postingModelBusinessService, JournalEntryBusinessService journalEntryBusinessService, SubsidiaryLedgerBusinessService subsidiaryLedgerBusinessService, GeneralLedgerBusinessService generalLedgerBusinessService, JournalEntryCrudService journalEntryCrudService, SubsidiaryLedgerCrudService subsidiaryLedgerCrudService, GeneralLedgerCrudService generalLedgerCrudService) {
        this.postingModelBusinessService = postingModelBusinessService;
        this.journalEntryBusinessService = journalEntryBusinessService;
        this.subsidiaryLedgerBusinessService = subsidiaryLedgerBusinessService;
        this.generalLedgerBusinessService = generalLedgerBusinessService;
        this.journalEntryCrudService = journalEntryCrudService;
        this.subsidiaryLedgerCrudService = subsidiaryLedgerCrudService;
        this.generalLedgerCrudService = generalLedgerCrudService;
    }


    @Override
    public VoucherFormulaAmount createVouchers(BookkeepingRequest bookkeepingRequest, RequestType requestType) {
        Date insertDateTime = new Date();
        VoucherFormulaAmount voucherFormulaAmount = new VoucherFormulaAmount();

        PostingModel postingModel = postingModelBusinessService.createPostingModel(requestType, bookkeepingRequest, voucherFormulaAmount);
        List<JournalEntry> journalEntries = journalEntryBusinessService.createJournalEntries(bookkeepingRequest, postingModel, insertDateTime);
        subsidiaryLedgerBusinessService.createSubsidiaryLedgers(journalEntries);
        List<GeneralLedger> generalLedgers = generalLedgerBusinessService.createGeneralLedgers(journalEntries);
        journalEntryCrudService.saveAll(journalEntries);
        generalLedgerCrudService.saveAll(generalLedgers);
        return voucherFormulaAmount;
    }

}
