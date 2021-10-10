package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import com.vosouq.bookkeeping.repository.SubsidiaryLedgerRepository;
import com.vosouq.bookkeeping.service.crud.SubsidiaryLedgerCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SubsidiaryLedgerCrudServiceImpl implements SubsidiaryLedgerCrudService {

    private final SubsidiaryLedgerRepository subsidiaryLedgerRepository;

    public SubsidiaryLedgerCrudServiceImpl(SubsidiaryLedgerRepository subsidiaryLedgerRepository) {
        this.subsidiaryLedgerRepository = subsidiaryLedgerRepository;
    }

    @Override
    public SubsidiaryLedger getByCode(Integer code) {
        return subsidiaryLedgerRepository.findByCode(code);
    }

    @Override
    public SubsidiaryLedger save(SubsidiaryLedger subsidiaryLedger) {
        return subsidiaryLedgerRepository.save(subsidiaryLedger);
    }

    @Override
    public List<SubsidiaryLedger> saveAll(List<SubsidiaryLedger> subsidiaryLedgers) {
        for (SubsidiaryLedger subsidiaryLedger : subsidiaryLedgers) {
            log.debug("{} is created.",subsidiaryLedger);
        }

        return (List<SubsidiaryLedger>) subsidiaryLedgerRepository.saveAll(subsidiaryLedgers);
    }
}
