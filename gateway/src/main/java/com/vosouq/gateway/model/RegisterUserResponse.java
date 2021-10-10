package com.vosouq.gateway.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResponse {

    private Long deviceId;
    private Integer retryPeriodInSeconds;

}
