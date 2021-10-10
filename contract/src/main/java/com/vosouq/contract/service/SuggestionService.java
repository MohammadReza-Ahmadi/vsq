package com.vosouq.contract.service;

import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.contract.model.Suggestion;
import com.vosouq.contract.model.SuggestionForSellerModel;
import com.vosouq.contract.model.User;
import com.vosouq.contract.utills.SortBy;

import java.util.List;

public interface SuggestionService {

    Suggestion save(Long contractTemplateId,
                    Long buyerId,
                    Long price,
                    String description);

    Suggestion save(Suggestion suggestion);

    void update(Long suggestionId,
                Long buyerId,
                Long price,
                String description);

    Suggestion findById(Long id);

    List<SuggestionForSellerModel> readAllForSeller(Long contractTemplateId,
                                                    Long sellerId,
                                                    Integer page,
                                                    Integer pageSize,
                                                    Boolean asc,
                                                    SortBy sortBy);

    void pin(Long suggestionId, Boolean toggle, Long onlineUserId);

    Suggestion confirm(Long suggestionId, Long contractTemplateId);

    void deny(Long suggestionId, Long sellerId);

    void delete(Long suggestionId, Long onlineUserId);

    BinaryDataHolder<User, User> getSides(Long suggestionId, Long onlineUserId);

    Suggestion readSuggestionForBuyer(Long contractTemplateId, Long buyerId);

    Suggestion findLatestForBuyer(Long contractTemplateId, Long buyerId);
}