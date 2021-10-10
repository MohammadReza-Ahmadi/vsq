package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.model.IbanAccount;
import com.vosouq.bookkeeping.repository.IbanAccountRepository;
import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.bookkeeping.service.crud.IbanAccountCrudService;
import com.vosouq.bookkeeping.service.validator.IbanAccountValidator;
import com.vosouq.bookkeeping.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IbanAccountCrudServiceImpl implements IbanAccountCrudService {

    private final IbanAccountRepository ibanAccountRepository;
    private final IbanAccountValidator ibanAccountValidator;
    private final UserBusinessService userBusinessService;

    public IbanAccountCrudServiceImpl(IbanAccountRepository ibanAccountRepository, IbanAccountValidator ibanAccountValidator, UserBusinessService userBusinessService) {
        this.ibanAccountRepository = ibanAccountRepository;
        this.ibanAccountValidator = ibanAccountValidator;
        this.userBusinessService = userBusinessService;
    }

    @Override
    public IbanAccount create(IbanAccount ibanAccount) {
        return ibanAccountRepository.save(ibanAccount);
    }

    @Override
    public IbanAccount getByIban(String iban) {
        /* validate iban by pattern */
        ibanAccountValidator.validateIban(iban);
        return ibanAccountRepository.findByIban(iban);
    }

    @Override
    public List<IbanAccount> getIBANs() {
        Long userId = userBusinessService.getCurrentUserId();
        return ibanAccountRepository.findAllByUserId(userId).orElse(AppUtil.getEmptyList());
    }
}
