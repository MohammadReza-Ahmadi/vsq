package com.vosouq.profile.user.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetByPhoneNumberResponse {

    private Long userId;
    private String name;
    private String imageUrl;
    private Integer score;

}
