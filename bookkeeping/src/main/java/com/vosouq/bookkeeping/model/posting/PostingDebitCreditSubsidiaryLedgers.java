package com.vosouq.bookkeeping.model.posting;

import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostingDebitCreditSubsidiaryLedgers {
    private RequestType requestType;
    private SubsidiaryLedger debitSubsidiaryLedger;
    private SubsidiaryLedger creditSubsidiaryLedger;
}
