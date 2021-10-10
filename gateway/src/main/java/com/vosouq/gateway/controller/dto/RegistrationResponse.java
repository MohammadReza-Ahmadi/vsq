package com.vosouq.gateway.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponse {

    private Long deviceId;
    private Integer retryPeriodInSeconds;

}
