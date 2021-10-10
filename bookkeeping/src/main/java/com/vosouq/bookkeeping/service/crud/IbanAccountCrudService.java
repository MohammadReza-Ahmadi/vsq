package com.vosouq.bookkeeping.service.crud;

import com.vosouq.bookkeeping.model.IbanAccount;

import java.util.List;

public interface IbanAccountCrudService {

    IbanAccount create(IbanAccount ibanAccount);

    IbanAccount getByIban(String iban);

    List<IbanAccount> getIBANs();
}
