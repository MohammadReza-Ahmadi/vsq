package com.vosouq.bookkeeping.service.crud;

import com.vosouq.bookkeeping.model.journalizing.JournalEntry;


public interface JournalCrudService {

    JournalEntry save(JournalEntry journalEntry);

}
