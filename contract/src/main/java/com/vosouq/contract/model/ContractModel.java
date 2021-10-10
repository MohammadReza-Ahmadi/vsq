package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractModel {

    private Long id;
    private ContractState state;
    private String title;
    private Long price;
    private Actor actor;
    private String actorName;
    private Long imageFile;
    private Long updateDate;
}
