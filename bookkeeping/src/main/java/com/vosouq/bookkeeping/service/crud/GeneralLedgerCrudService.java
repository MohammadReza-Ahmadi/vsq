package com.vosouq.bookkeeping.service.crud;


import com.vosouq.bookkeeping.model.journalizing.GeneralLedger;

import java.util.List;

public interface GeneralLedgerCrudService {

    GeneralLedger getByCode(Integer code);

    GeneralLedger save(GeneralLedger generalLedger);

    List<GeneralLedger> saveAll(List<GeneralLedger> generalLedgers);
}
