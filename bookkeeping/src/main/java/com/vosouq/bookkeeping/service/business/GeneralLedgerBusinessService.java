package com.vosouq.bookkeeping.service.business;


import com.vosouq.bookkeeping.model.journalizing.GeneralLedger;
import com.vosouq.bookkeeping.model.journalizing.JournalEntry;

import java.util.List;

public interface GeneralLedgerBusinessService {

    List<GeneralLedger> createGeneralLedgers(List<JournalEntry> journalEntries);
}
