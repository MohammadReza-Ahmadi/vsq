package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.model.journalizing.GeneralLedger;
import org.springframework.data.repository.CrudRepository;


public interface GeneralLedgerRepository extends CrudRepository<GeneralLedger,Integer> {

    GeneralLedger findByCode(Integer code);

}
