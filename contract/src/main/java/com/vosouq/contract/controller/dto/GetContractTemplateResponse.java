package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetContractTemplateResponse {

    private ContractType contractType;
    private String title;
    private Long price;
    private String province;
    private String city;
    private String neighbourhood;
    private String description;
    private Boolean repeatable;
    private Integer numberOfRepeats;
    private Boolean viewable;
    private Boolean favorite;
    private Long createDate;
    private Long updateDate;
    private List<Long> fileIds;
    private User seller;

    @Override
    public String toString() {
        return "GetContractTemplateResponse{" +
                "contractType=" + contractType +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", description='" + description + '\'' +
                ", repeatable=" + repeatable +
                ", numberOfRepeats=" + numberOfRepeats +
                ", viewable=" + viewable +
                ", favorite=" + favorite +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", fileIds=" + fileIds +
                ", seller=" + seller +
                '}';
    }
}