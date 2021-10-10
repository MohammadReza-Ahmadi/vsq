package com.vosouq.profile.user.controller.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePasswordResponse {

    private long userId;
    private String phoneNumber;

}
