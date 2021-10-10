package com.vosouq.bookkeeping.controller.crud;


import com.vosouq.bookkeeping.controller.dto.requests.IbanAccountRequest;
import com.vosouq.bookkeeping.controller.dto.responses.IbanAccountResponse;
import com.vosouq.bookkeeping.model.IbanAccount;
import com.vosouq.bookkeeping.service.business.IbanAccountBusinessService;
import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@VosouqRestController
@RequestMapping(value = "/iban-accounts")
public class IbanAccountCrudController {

    private final IbanAccountBusinessService ibanAccountBusinessService;

    public IbanAccountCrudController(IbanAccountBusinessService ibanAccountBusinessService) {
        this.ibanAccountBusinessService = ibanAccountBusinessService;
    }

    @NoContent
    @PostMapping
    public void create(@RequestBody @Valid IbanAccountRequest ibanAccountRequest){
        ibanAccountBusinessService.create(new IbanAccount(ibanAccountRequest.getIban(),ibanAccountRequest.getBankName()));
    }

    @GetMapping
    public List<IbanAccountResponse> getIBANs(){
       return ibanAccountBusinessService.getIBANs();
    }
}
