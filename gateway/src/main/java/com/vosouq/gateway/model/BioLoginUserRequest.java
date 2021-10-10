package com.vosouq.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BioLoginUserRequest {

    private Long deviceId;
    private String currentBioToken;
    private String newBioToken;

}
