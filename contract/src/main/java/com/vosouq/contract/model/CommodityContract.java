package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value = "COMMODITY")
public class CommodityContract extends Contract {

    @NotNull
    @Column(name = "price_per_unit")
    private Long pricePerUnit;

    @NotNull
    @Column(name = "quantity")
    private Integer quantity;

    @Column(name= "unit")
    @Enumerated(EnumType.STRING)
    private Unit unit;

}
