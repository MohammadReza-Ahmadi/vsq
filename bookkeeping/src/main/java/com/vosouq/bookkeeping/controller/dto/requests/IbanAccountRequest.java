package com.vosouq.bookkeeping.controller.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IbanAccountRequest {
    @NotBlank(message = "iban is mandatory!")
    private String iban;
    @NotBlank(message = "bankName is mandatory!")
    private String bankName;
}
