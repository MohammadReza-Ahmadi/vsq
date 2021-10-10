package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractTemplateModel {

    private Long id;
    private Long sellerId;
    private String sellerName;
    private Timestamp createDate;
    private String title;
    private Long price;
    private Boolean favorite;
    private Boolean repeatable;
    private Boolean viewable;
    private Actor actor;
    private Long fileId;
    private Integer numberOfRepeats;

    public ContractTemplateModel(Long id,
                                 Long sellerId,
                                 Timestamp createDate,
                                 String title,
                                 Long price,
                                 Boolean favorite,
                                 Boolean repeatable,
                                 Boolean viewable,
                                 Integer numberOfRepeats) {
        this.id = id;
        this.sellerId = sellerId;
        this.createDate = createDate;
        this.title = title;
        this.price = price;
        this.favorite = favorite;
        this.repeatable = repeatable;
        this.viewable = viewable;
        this.numberOfRepeats = numberOfRepeats;
    }

}
