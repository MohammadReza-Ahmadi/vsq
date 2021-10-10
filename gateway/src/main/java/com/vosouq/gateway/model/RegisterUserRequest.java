package com.vosouq.gateway.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

    private String udid;
    private String deviceName;
    private String os;
    private String osVersion;
    private String appVersion;
    private String phoneNumber;

}
