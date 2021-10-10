package com.vosouq.contract.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetAllTurnoversResponse {

    private Long id;
    private Long updateDate;
    private String title;
    private Long price;
    private Side side;
    private String sideName;
    private Long imageFile;
}
