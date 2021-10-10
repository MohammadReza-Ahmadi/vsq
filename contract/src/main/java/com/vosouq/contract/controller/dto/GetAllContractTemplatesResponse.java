package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.SuggestionState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllContractTemplatesResponse {
    public GetAllContractTemplatesResponse(Long id,
                                           Long createDate,
                                           String title,
                                           Long totalPrice,
                                           Long fileId,
                                           Side side,
                                           String sideName,
                                           Boolean favorite,
                                           Boolean repeatable,
                                           Boolean viewable,
                                           Integer numberOfRepeats) {
        this.id = id;
        this.createDate = createDate;
        this.title = title;
        this.totalPrice = totalPrice;
        this.fileId = fileId;
        this.side = side;
        this.sideName = sideName;
        this.favorite = favorite;
        this.repeatable = repeatable;
        this.viewable = viewable;
        this.numberOfRepeats = numberOfRepeats;
    }

    private Long id;
    private Long createDate;
    private String title;
    private Long totalPrice;
    private Long fileId;
    private Side side;
    private String sideName;
    private Boolean favorite;
    private Boolean repeatable;
    private Boolean viewable;
    private Integer numberOfRepeats;
    private SuggestionState suggestionState;

    @Override
    public String toString() {
        return "GetAllContractTemplatesResponse{" +
                "id=" + id +
                ", createDate=" + createDate +
                ", title='" + title + '\'' +
                ", totalPrice=" + totalPrice +
                ", fileId=" + fileId +
                ", side=" + side +
                ", sideName='" + sideName + '\'' +
                ", favorite=" + favorite +
                ", repeatable=" + repeatable +
                ", numberOfRepeats=" + numberOfRepeats +
                ", viewable=" + viewable +
                '}';
    }
}
