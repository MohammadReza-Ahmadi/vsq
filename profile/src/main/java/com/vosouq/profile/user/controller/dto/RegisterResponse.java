package com.vosouq.profile.user.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {

    private Long deviceId;
    private Integer retryPeriodInSeconds;

}
