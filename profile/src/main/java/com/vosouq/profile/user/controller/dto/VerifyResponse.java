package com.vosouq.profile.user.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyResponse {

    private long userId;
    private String phoneNumber;
    private boolean kycCompleted;
    private boolean passwordDefined;
}
