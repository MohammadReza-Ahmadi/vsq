package com.vosouq.gateway.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserResponse {

    private long userId;
    private String phoneNumber;
    private boolean kycCompleted;
    private boolean passwordDefined;

}
