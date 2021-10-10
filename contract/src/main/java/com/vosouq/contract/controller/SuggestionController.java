package com.vosouq.contract.controller;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.controller.mapper.SuggestionMapper;
import com.vosouq.contract.model.Suggestion;
import com.vosouq.contract.model.User;
import com.vosouq.contract.service.SuggestionService;
import com.vosouq.contract.utills.SortBy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@VosouqRestController
@RequestMapping("/suggestions")
@Slf4j
public class SuggestionController {

    private final SuggestionService suggestionService;
    private final OnlineUser onlineUser;

    @Autowired
    public SuggestionController(
            SuggestionService suggestionService,
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                    OnlineUser onlineUser) {
        this.suggestionService = suggestionService;
        this.onlineUser = onlineUser;
    }

    @PostMapping
    @Created
    public CreateSuggestionResponse create(@Valid @RequestBody CreateSuggestionRequest request) {
        Suggestion suggestion =
                suggestionService.save(
                        request.getContractTemplateId(),
                        onlineUser.getUserId(),
                        request.getPrice(),
                        request.getDescription());

        return new CreateSuggestionResponse(suggestion.getId());
    }

    @PutMapping("/{suggestionId}")
    @NoContent
    public void update(@Valid @PathVariable Long suggestionId,
                       @Valid @RequestBody UpdateSuggestionRequest request) {

        suggestionService.update(
                suggestionId,
                onlineUser.getUserId(),
                request.getPrice(),
                request.getDescription());
    }

    @GetMapping(value = "/seller/{contractTemplateId}")
    public List<SuggestionForSellerResponse> readAllForSeller(@PathVariable @NotNull Long contractTemplateId,
                                                              @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
                                                              @RequestParam(required = false, defaultValue = "25") @Max(200) @Min(1) Integer pageSize,
                                                              @RequestParam(required = false, defaultValue = "false") Boolean asc,
                                                              @RequestParam(required = false, defaultValue = "NONE") SortBy sortBy) {

        return suggestionService
                .readAllForSeller(
                        contractTemplateId,
                        onlineUser.getUserId(),
                        page,
                        pageSize,
                        asc,
                        sortBy)
                .stream()
                .map(SuggestionMapper::map)
                .collect(Collectors.toList());

    }

    @GetMapping(value = "/buyer/{contractTemplateId}")
    public SuggestionForBuyerResponse readForBuyer(@PathVariable @NotNull Long contractTemplateId) {
        return SuggestionMapper.map(suggestionService.readSuggestionForBuyer(contractTemplateId, onlineUser.getUserId()));
    }

    @GetMapping("/{suggestionId}/sides")
    public GetSuggestionSidesResponse getSuggestionSides(@PathVariable @Valid Long suggestionId){
        BinaryDataHolder<User, User> users = suggestionService.getSides(suggestionId, onlineUser.getUserId());
        return new GetSuggestionSidesResponse(users.getFirstType(), users.getSecondType());
    }

    @PutMapping("/{suggestionId}/pin")
    @NoContent
    public void pin(@Valid @PathVariable Long suggestionId, @Valid @RequestParam Boolean toggle) {
        suggestionService.pin(
                suggestionId,
                toggle,
                onlineUser.getUserId());
    }

    @DeleteMapping("/{suggestionId}/deny")
    @NoContent
    public void deny(@Valid @PathVariable Long suggestionId) {
        suggestionService.deny(
                suggestionId,
                onlineUser.getUserId());
    }

    @DeleteMapping("/{suggestionId}")
    public void delete(@Valid @PathVariable Long suggestionId){
        suggestionService.delete(suggestionId, onlineUser.getUserId());
    }
}