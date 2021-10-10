package com.vosouq.profile.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String phoneNumber;
    private String nationalCode;
    private String serial;
    private String birthDate;
    private String firstName;
    private String lastName;
    private String profileImageAddress;

}
