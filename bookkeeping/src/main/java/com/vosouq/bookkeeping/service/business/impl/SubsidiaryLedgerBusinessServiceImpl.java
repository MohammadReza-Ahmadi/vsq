package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import com.vosouq.bookkeeping.service.business.SubsidiaryLedgerBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vosouq.bookkeeping.util.AppUtil.getZeroIfNull;

@Slf4j
@Service
public class SubsidiaryLedgerBusinessServiceImpl implements SubsidiaryLedgerBusinessService {

    @Override
    public List<SubsidiaryLedger> createSubsidiaryLedgers(List<JournalEntry> journalEntries) {
        Map<Integer, SubsidiaryLedger> subsidiaryLedgerMap = new HashMap<>();

        /* loop on JournalEntries to fetch each SubsidiaryLedger and update its Debit, Credit and SubsidiaryAccount balance */
        for (JournalEntry journalEntry : journalEntries) {

            /* if subsidiaryLedger is null add SubsidiaryLedger of JournalEntry to map */
            subsidiaryLedgerMap.computeIfAbsent(journalEntry.getSubsidiaryLedger().getCode(), k -> journalEntry.getSubsidiaryLedger());

            /* get subsidiaryLedger from map */
            SubsidiaryLedger subsidiaryLedger = subsidiaryLedgerMap.get(journalEntry.getSubsidiaryLedger().getCode());

            /* if subsidiaryLedger is not null update its amount values */
            /* update subsidiaryLedger Debit and Credit amounts */
            subsidiaryLedger.setDebit(getZeroIfNull(subsidiaryLedger.getDebit()).add(getZeroIfNull(journalEntry.getDebit())));
            subsidiaryLedger.setCredit(getZeroIfNull(subsidiaryLedger.getCredit()).add(getZeroIfNull(journalEntry.getCredit())));

            if(subsidiaryLedger.getNormalBalance().isDebit()) {
                subsidiaryLedger.setBalance(getZeroIfNull(subsidiaryLedger.getBalance()).add(getZeroIfNull(journalEntry.getDebit())));
                subsidiaryLedger.setBalance(getZeroIfNull(subsidiaryLedger.getBalance()).subtract(getZeroIfNull(journalEntry.getCredit())));
            }

            if(subsidiaryLedger.getNormalBalance().isCredit()) {
                subsidiaryLedger.setBalance(getZeroIfNull(subsidiaryLedger.getBalance()).add(getZeroIfNull(journalEntry.getCredit())));
                subsidiaryLedger.setBalance(getZeroIfNull(subsidiaryLedger.getBalance()).subtract(getZeroIfNull(journalEntry.getDebit())));
            }

            log.debug("SubsidiaryLeger Debit and Credit amounts are updated. {}",subsidiaryLedger);
        }

        return new ArrayList<>(subsidiaryLedgerMap.values());
    }
}
