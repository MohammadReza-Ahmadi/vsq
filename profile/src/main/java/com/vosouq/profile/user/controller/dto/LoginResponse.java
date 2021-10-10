package com.vosouq.profile.user.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private long userId;
    private String phoneNumber;

}
