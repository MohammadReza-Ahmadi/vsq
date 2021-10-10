package com.vosouq.bookkeeping.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GatewayPaymentRedirect {
    private String refId;
    private String mobileNo;
}
