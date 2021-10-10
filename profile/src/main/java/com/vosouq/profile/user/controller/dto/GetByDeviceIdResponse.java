package com.vosouq.profile.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetByDeviceIdResponse {

    private Long userId;
    private String phoneNumber;
    private String nationalCode;

}
