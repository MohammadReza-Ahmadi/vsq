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
public class SuggestionForBuyerResponse {

    private Long id;
    private Long updateDate;
    private Long price;
    private String description;
    private SuggestionState suggestionState;
}