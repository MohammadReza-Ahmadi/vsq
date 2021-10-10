package com.vosouq.bookkeeping.controller.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContractIpgPaymentRequest {
    private Long id;
    private String mobileNo;
    private String description;
}
