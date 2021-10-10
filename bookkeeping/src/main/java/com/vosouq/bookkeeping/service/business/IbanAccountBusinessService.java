package com.vosouq.bookkeeping.service.business;

import com.vosouq.bookkeeping.controller.dto.responses.IbanAccountResponse;
import com.vosouq.bookkeeping.model.IbanAccount;

import java.util.List;

public interface IbanAccountBusinessService {

    IbanAccount create(IbanAccount ibanAccount);

    List<IbanAccountResponse> getIBANs();
}
