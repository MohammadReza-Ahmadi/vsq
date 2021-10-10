package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.model.journalizing.GeneralLedger;
import com.vosouq.bookkeeping.repository.GeneralLedgerRepository;
import com.vosouq.bookkeeping.service.crud.GeneralLedgerCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GeneralLedgerCrudServiceImpl implements GeneralLedgerCrudService {

    private final GeneralLedgerRepository generalLedgerRepository;

    public GeneralLedgerCrudServiceImpl(GeneralLedgerRepository generalLedgerRepository) {
        this.generalLedgerRepository = generalLedgerRepository;
    }

    @Override
    public GeneralLedger getByCode(Integer code) {
        return generalLedgerRepository.findByCode(code);
    }

    @Override
    public GeneralLedger save(GeneralLedger generalLedger) {
        return generalLedgerRepository.save(generalLedger);
    }

    @Override
    public List<GeneralLedger> saveAll(List<GeneralLedger> generalLedgers) {
        for (GeneralLedger generalLedger : generalLedgers) {
            log.debug("{} is created.",generalLedger);
        }

        return (List<GeneralLedger>) generalLedgerRepository.saveAll(generalLedgers);
    }
}
