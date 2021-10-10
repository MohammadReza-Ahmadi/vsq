package com.vosouq.kyc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddKycInfoRequest {

    private String nationalCode;
    private String serial;
    private String birthDate;
    private String firstName;
    private String lastName;

}
