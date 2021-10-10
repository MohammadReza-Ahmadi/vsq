package com.vosouq.kyc.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyVideoResponse {

    private int resultCode;
    private String resultMessage;
    private String firstName;
    private String lastName;

}
