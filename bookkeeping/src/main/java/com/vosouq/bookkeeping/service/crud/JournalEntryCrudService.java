package com.vosouq.bookkeeping.service.crud;

import com.vosouq.bookkeeping.model.journalizing.JournalEntry;

import java.util.List;


public interface JournalEntryCrudService {

    JournalEntry save(JournalEntry journalEntry);

    Iterable<JournalEntry> saveAll(List<JournalEntry> journalEntries);

}
