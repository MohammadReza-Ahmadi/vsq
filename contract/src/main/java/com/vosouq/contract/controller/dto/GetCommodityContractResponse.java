package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.Unit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetCommodityContractResponse extends GetContractResponse {

    private Long pricePerUnit;
    private Integer quantity;
    private Unit unit;
}
