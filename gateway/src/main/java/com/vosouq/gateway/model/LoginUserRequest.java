package com.vosouq.gateway.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserRequest {
    
    private Long deviceId;
    private String password;
    private String bioToken;

}
