package com.vosouq.bookkeeping.service.business;

import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.model.posting.PostingModel;

import java.util.Date;
import java.util.List;

public interface JournalEntryBusinessService {

    List<JournalEntry> createJournalEntries(BookkeepingRequest bookkeepingRequest, PostingModel postingModel, Date insertDateTime);

}
