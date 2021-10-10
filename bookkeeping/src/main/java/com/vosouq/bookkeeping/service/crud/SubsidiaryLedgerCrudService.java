package com.vosouq.bookkeeping.service.crud;


import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;

import java.util.List;

public interface SubsidiaryLedgerCrudService {

    SubsidiaryLedger getByCode(Integer code);

    SubsidiaryLedger save(SubsidiaryLedger subsidiaryLedger);

    List<SubsidiaryLedger> saveAll(List<SubsidiaryLedger> subsidiaryLedgers);
}
