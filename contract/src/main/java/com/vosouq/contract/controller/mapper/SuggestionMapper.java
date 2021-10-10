package com.vosouq.contract.controller.mapper;

import com.vosouq.contract.controller.dto.CreateSuggestionRequest;
import com.vosouq.contract.controller.dto.SuggestionForBuyerResponse;
import com.vosouq.contract.controller.dto.SuggestionForSellerResponse;
import com.vosouq.contract.model.Suggestion;
import com.vosouq.contract.model.SuggestionForSellerModel;

public class SuggestionMapper {

    public static Suggestion map(CreateSuggestionRequest request) {
        Suggestion entity = null;
        if (request != null) {
            entity = new Suggestion();
            entity.setPrice(request.getPrice());
            entity.setDescription(request.getDescription());
        }
        return entity;
    }

    public static SuggestionForSellerResponse map(SuggestionForSellerModel model) {
        SuggestionForSellerResponse response = new SuggestionForSellerResponse();
        response.setId(model.getId());
        response.setCreateDate(model.getCreateDate());
        response.setUpdateDate(model.getUpdateDate());
        response.setPrice(model.getPrice());
        response.setDescription(model.getDescription());
        response.setPinned(model.getPinned());
        response.setSuggestionState(model.getSuggestionState());
        response.setEnabled(model.getEnabled());
        response.setBuyer(model.getBuyer());

        return response;
    }

    public static SuggestionForBuyerResponse map(Suggestion suggestion) {
        return new SuggestionForBuyerResponse(
                suggestion.getId(),
                suggestion.getUpdateDate() == null ? null : suggestion.getUpdateDate().getTime(),
                suggestion.getPrice(),
                suggestion.getDescription(),
                suggestion.getSuggestionState());
    }

}