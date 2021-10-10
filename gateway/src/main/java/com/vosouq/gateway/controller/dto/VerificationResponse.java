package com.vosouq.gateway.controller.dto;

import com.vosouq.gateway.model.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationResponse {

    private String token;
    private TokenType tokenType;
    private boolean kycCompleted;
    private boolean passwordDefined;

}
