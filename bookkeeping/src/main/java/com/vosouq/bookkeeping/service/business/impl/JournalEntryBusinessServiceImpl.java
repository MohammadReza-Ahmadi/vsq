package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.model.journalizing.JournalizingPage;
import com.vosouq.bookkeeping.model.posting.PostingModel;
import com.vosouq.bookkeeping.model.posting.VoucherModel;
import com.vosouq.bookkeeping.model.posting.VoucherRowModel;
import com.vosouq.bookkeeping.service.business.JournalEntryBusinessService;
import com.vosouq.bookkeeping.service.business.JournalizingPageBusinessService;
import com.vosouq.bookkeeping.service.crud.PostingModelCrudService;
import com.vosouq.bookkeeping.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JournalEntryBusinessServiceImpl implements JournalEntryBusinessService {

    private final PostingModelCrudService postingModelCrudService;
    private final JournalizingPageBusinessService journalizingPageBusinessService;

    public JournalEntryBusinessServiceImpl(PostingModelCrudService postingModelCrudService, JournalizingPageBusinessService journalizingPageBusinessService) {
        this.postingModelCrudService = postingModelCrudService;
        this.journalizingPageBusinessService = journalizingPageBusinessService;
    }


    /**
     * This method is used to creating JournalEntryList based on input parameters.
     *
     * @param bookkeepingRequest should be find from db, this object was saved when the bookkeeping request is created.
     * @param postingModel       indicates the PostingModel which provide voucher creation details.
     * @param insertDateTime
     * @return List of JournalEntry for saving in db.
     */
    @Override
    public List<JournalEntry> createJournalEntries(BookkeepingRequest bookkeepingRequest, PostingModel postingModel, Date insertDateTime) {
        /* find today JournalizingPage from db */
        JournalizingPage journalizingPage = journalizingPageBusinessService.getCurrentJournalizingPage();

        /* create journalEntry list from voucherRowModel list */
        List<JournalEntry> journalEntries = new ArrayList<>();
        for (VoucherModel voucherModel : postingModel.getVoucherModels()) {
            for (VoucherRowModel voucherRowModel : voucherModel.getVoucherRowModels()) {
                /* build journalEntry and add to list */
                journalEntries.add(buildJournalEntry(bookkeepingRequest, journalizingPage, voucherRowModel, insertDateTime));
            }
        }

        return journalEntries;
    }

    private JournalEntry buildJournalEntry(BookkeepingRequest bookkeepingRequest, JournalizingPage journalizingPage, VoucherRowModel voucherRowModel, Date insertDateTime) {
        JournalEntry journalEntry = new JournalEntry();
        journalEntry.setBookkeepingRequest(bookkeepingRequest);
        journalEntry.setJournalizingPage(journalizingPage);
        journalEntry.setSubsidiaryLedger(voucherRowModel.getSubsidiaryLedger());

        if (AppUtil.isNotNull(voucherRowModel.getNarrative1())) {
            journalEntry.setNarrative1(voucherRowModel.getNarrative1().concat(AppUtil.getEmptyStrIfNull(bookkeepingRequest.getUserId())));
        }

        if (AppUtil.isNotNull(voucherRowModel.getNarrative2())) {
            journalEntry.setNarrative2(voucherRowModel.getNarrative2().concat(AppUtil.getEmptyStrIfNull(bookkeepingRequest.getRequesterId())));
        }

        if (AppUtil.isNotNull(voucherRowModel.getNarrative3())) {
            journalEntry.setNarrative3(voucherRowModel.getNarrative3());
        }

        journalEntry.setInsertDateTime(insertDateTime);
        journalEntry.setDescription(bookkeepingRequest.getDescription());

        /* set debitAccount and amount if normalBalance is Debit */
        if (voucherRowModel.getNormalBalance().isDebit()) {
            journalEntry.setDebit(voucherRowModel.getAmount());
            journalEntry.setDebitAccount(bookkeepingRequest.getDebitAccount());
            journalEntry.setDebitSubsidiaryLedger(voucherRowModel.getSubsidiaryLedger());
        }

        /* set creditAccount and amount if normalBalance is Credit */
        if (voucherRowModel.getNormalBalance().isCredit()) {
            journalEntry.setCredit(voucherRowModel.getAmount());
            journalEntry.setCreditAccount(bookkeepingRequest.getCreditAccount());
            journalEntry.setCreditSubsidiaryLedger(voucherRowModel.getSubsidiaryLedger());
        }

        log.trace("{} is created.", journalEntry);
        return journalEntry;
    }
}
