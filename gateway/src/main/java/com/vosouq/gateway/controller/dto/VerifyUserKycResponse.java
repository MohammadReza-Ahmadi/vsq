package com.vosouq.gateway.controller.dto;

import com.vosouq.gateway.model.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VerifyUserKycResponse {

    private String token;
    private TokenType tokenType;

}
