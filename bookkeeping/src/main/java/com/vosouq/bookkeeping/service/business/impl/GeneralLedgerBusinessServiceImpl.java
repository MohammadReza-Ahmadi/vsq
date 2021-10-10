package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.model.journalizing.GeneralLedger;
import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.service.business.GeneralLedgerBusinessService;
import com.vosouq.bookkeeping.service.crud.SubsidiaryLedgerCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vosouq.bookkeeping.util.AppUtil.getZeroIfNull;

@Slf4j
@Service
public class GeneralLedgerBusinessServiceImpl implements GeneralLedgerBusinessService {

    private final SubsidiaryLedgerCrudService subsidiaryLedgerCrudService;

    public GeneralLedgerBusinessServiceImpl(SubsidiaryLedgerCrudService subsidiaryLedgerCrudService) {
        this.subsidiaryLedgerCrudService = subsidiaryLedgerCrudService;
    }


    @Override
    public List<GeneralLedger> createGeneralLedgers(List<JournalEntry> journalEntries) {
        Map<Integer, GeneralLedger> generalLedgerMap = new HashMap<>();
        for (JournalEntry journalEntry : journalEntries) {

            /* if GeneralLedger is null add GeneralLedger of subsidiaryLeger to map */
            generalLedgerMap.computeIfAbsent(journalEntry.getSubsidiaryLedger().getGeneralLedger().getCode(), k -> journalEntry.getSubsidiaryLedger().getGeneralLedger());

            /* get generalLedger from map */
            GeneralLedger generalLedger = generalLedgerMap.get(journalEntry.getSubsidiaryLedger().getGeneralLedger().getCode());

            /* update generalLedger Debit and Credit amounts */
            generalLedger.setDebit(getZeroIfNull(generalLedger.getDebit()).add(getZeroIfNull(journalEntry.getDebit())));
            generalLedger.setCredit(getZeroIfNull(generalLedger.getCredit()).add(getZeroIfNull(journalEntry.getCredit())));

            if(generalLedger.getNormalBalance().isDebit()) {
                generalLedger.setBalance(getZeroIfNull(generalLedger.getBalance()).add(getZeroIfNull(journalEntry.getDebit())));
                generalLedger.setBalance(getZeroIfNull(generalLedger.getBalance()).subtract(getZeroIfNull(journalEntry.getCredit())));
            }

            if(generalLedger.getNormalBalance().isCredit()) {
                generalLedger.setBalance(getZeroIfNull(generalLedger.getBalance()).add(getZeroIfNull(journalEntry.getCredit())));
                generalLedger.setBalance(getZeroIfNull(generalLedger.getBalance()).subtract(getZeroIfNull(journalEntry.getDebit())));
            }
        }

        return new ArrayList<>(generalLedgerMap.values());
    }
}
