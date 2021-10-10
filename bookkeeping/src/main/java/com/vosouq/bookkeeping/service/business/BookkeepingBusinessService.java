package com.vosouq.bookkeeping.service.business;

import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.domainobject.VoucherFormulaAmount;
import com.vosouq.bookkeeping.enumeration.RequestType;

public interface BookkeepingBusinessService {

    VoucherFormulaAmount createVouchers(BookkeepingRequest bookkeepingRequest, RequestType requestType);
}
