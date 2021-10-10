package com.vosouq.gateway.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePasswordRequest {
    
    private Long deviceId;
    private String password;
    private String confirmPassword;
    private String bioToken;

}
