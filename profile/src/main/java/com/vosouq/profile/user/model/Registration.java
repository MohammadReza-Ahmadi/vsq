package com.vosouq.profile.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    private Long deviceId;
    private Integer retryPeriodInSeconds;
}
