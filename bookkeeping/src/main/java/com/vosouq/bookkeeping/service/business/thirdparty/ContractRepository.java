package com.vosouq.bookkeeping.service.business.thirdparty;

import com.vosouq.bookkeeping.controller.dto.responses.ContractGetAmountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "contract")
public interface ContractRepository {

    @GetMapping(value = "/currents/{contractId}/payment/inquiry")
    ContractGetAmountResponse getContractPaymentAmount(@PathVariable Long contractId);

    @PostMapping(value = "/currents/{contractId}/payment/{amount}")
    void informContractBy‌PaidAmount(@PathVariable Long contractId, @PathVariable Long amount);

}
