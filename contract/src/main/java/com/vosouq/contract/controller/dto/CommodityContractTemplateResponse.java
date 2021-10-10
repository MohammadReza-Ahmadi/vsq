package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.Unit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommodityContractTemplateResponse extends GetContractTemplateResponse {

    private Long pricePerUnit;
    private Integer quantity;
    private Unit unit;

    @Override
    public String toString() {
        return super.toString() +
                " CommodityContractTemplateResponse{" +
                "pricePerUnit=" + pricePerUnit +
                ", quantity=" + quantity +
                ", unit=" + unit +
                ", unit=" + unit +
                '}';
    }
}