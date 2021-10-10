package com.vosouq.bookkeeping.service.business;


import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;

import java.util.List;

public interface SubsidiaryLedgerBusinessService {

    List<SubsidiaryLedger> createSubsidiaryLedgers(List<JournalEntry> journalEntries);

}
