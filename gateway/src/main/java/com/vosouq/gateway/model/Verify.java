package com.vosouq.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Verify {

    private String token;
    private TokenType tokenType;
    private boolean kycCompleted;
    private boolean passwordDefined;

    public Verify(boolean kycCompleted, boolean passwordDefined) {
        this.kycCompleted = kycCompleted;
        this.passwordDefined = passwordDefined;
    }
}
