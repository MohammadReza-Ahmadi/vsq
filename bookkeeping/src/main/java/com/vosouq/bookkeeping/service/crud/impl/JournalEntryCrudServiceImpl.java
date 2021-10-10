package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.repository.JournalEntryRepository;
import com.vosouq.bookkeeping.service.crud.JournalEntryCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class JournalEntryCrudServiceImpl implements JournalEntryCrudService {

    private final JournalEntryRepository journalEntryRepository;

    public JournalEntryCrudServiceImpl(JournalEntryRepository journalEntryRepository) {
        this.journalEntryRepository = journalEntryRepository;
    }


    @Override
    public JournalEntry save(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    @Override
    public Iterable<JournalEntry> saveAll(List<JournalEntry> journalEntries) {
        for (JournalEntry journalEntry : journalEntries) {
            log.debug("{} is created.",journalEntry);
        }
        return journalEntryRepository.saveAll(journalEntries);
    }
}
