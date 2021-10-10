package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.SuggestionState;
import com.vosouq.contract.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionForSellerResponse {

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