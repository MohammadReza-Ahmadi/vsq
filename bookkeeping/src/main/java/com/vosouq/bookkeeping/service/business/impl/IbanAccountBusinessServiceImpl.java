package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.controller.dto.responses.IbanAccountResponse;
import com.vosouq.bookkeeping.exception.iban.IbanValueDuplicateException;
import com.vosouq.bookkeeping.model.IbanAccount;
import com.vosouq.bookkeeping.service.business.IbanAccountBusinessService;
import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.bookkeeping.service.crud.IbanAccountCrudService;
import com.vosouq.bookkeeping.service.validator.IbanAccountValidator;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IbanAccountBusinessServiceImpl implements IbanAccountBusinessService {

    private final IbanAccountCrudService ibanAccountCrudService;
    private final IbanAccountValidator ibanAccountValidator;
    private final UserBusinessService userBusinessService;

    public IbanAccountBusinessServiceImpl(IbanAccountCrudService ibanAccountCrudService, IbanAccountValidator ibanAccountValidator, UserBusinessService userBusinessService) {
        this.ibanAccountCrudService = ibanAccountCrudService;
        this.ibanAccountValidator = ibanAccountValidator;
        this.userBusinessService = userBusinessService;
    }

    @Override
    public IbanAccount create(IbanAccount ibanAccount) {
        /* set online userId */
        ibanAccount.setUserId(userBusinessService.getCurrentUserId());
        /* validate iban by pattern */
        ibanAccountValidator.validateIban(ibanAccount.getIban());
        /* save ibanAccount in db */
        try {
            ibanAccountCrudService.create(ibanAccount);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new IbanValueDuplicateException();
            }
            throw e;
        }
        return ibanAccount;
    }

    @Override
    public List<IbanAccountResponse> getIBANs() {
        return ibanAccountCrudService.getIBANs().stream().filter(Objects::nonNull)
                .map(ibc -> new IbanAccountResponse(ibc.getIban(), ibc.getBankName()))
                .collect(Collectors.toList());
    }
}
