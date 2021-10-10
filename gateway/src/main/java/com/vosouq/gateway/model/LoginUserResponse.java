package com.vosouq.gateway.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResponse {

    private Long userId;
    private String phoneNumber;

}
