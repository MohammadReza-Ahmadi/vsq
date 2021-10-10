package com.vosouq.gateway.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyUserRequest {

    private Long deviceId;
    private String smsCode;

}
