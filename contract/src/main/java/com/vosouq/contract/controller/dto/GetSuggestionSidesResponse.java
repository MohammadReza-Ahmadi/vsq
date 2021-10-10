package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetSuggestionSidesResponse {

    private User seller;
    private User buyer;
}
