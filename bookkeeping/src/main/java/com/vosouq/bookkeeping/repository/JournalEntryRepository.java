package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends CrudRepository<JournalEntry, Long> {

}
