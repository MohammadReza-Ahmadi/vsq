package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionForSellerModel {

    private Long id;
    private Long createDate;
    private Long updateDate;
    private Long price;
    private String description;
    private Boolean pinned;
    private SuggestionState suggestionState;
    private Boolean enabled;
    private User buyer;
}