package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.ContractState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetAllContractsResponse {

    private Long id;
    private Long updateDate;
    private ContractState state;
    private String title;
    private Long price;
    private Side side;
    private String sideName;
    private Long imageFile;
}
