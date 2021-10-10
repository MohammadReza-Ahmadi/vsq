package com.vosouq.contract.service.impl;

import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.commons.util.MessageUtil;
import com.vosouq.contract.exception.*;
import com.vosouq.contract.model.*;
import com.vosouq.contract.repository.SuggestionRepository;
import com.vosouq.contract.service.ContractTemplateService;
import com.vosouq.contract.service.MessageService;
import com.vosouq.contract.service.NotificationService;
import com.vosouq.contract.service.SuggestionService;
import com.vosouq.contract.service.feign.ProfileServiceClient;
import com.vosouq.contract.service.feign.ScoringCommunicatorClient;
import com.vosouq.contract.utills.SortBy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.vosouq.contract.utills.MessageConstants.CREATE_NEW_SUGGESTION_PUSH_MESSAGE;
import static com.vosouq.contract.utills.MessageConstants.SUGGESTION_DENIED_PUSH_MESSAGE;
import static com.vosouq.contract.utills.TimeUtil.nowInTimestamp;

@Service
@Transactional
@Slf4j
public class SuggestionServiceImpl implements SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final ContractTemplateService contractTemplateService;
    private final ProfileServiceClient profileServiceClient;
    private final MessageService messageService;
    private final NotificationService notificationService;
    private final ScoringCommunicatorClient scoringCommunicatorClient;

    @Autowired
    public SuggestionServiceImpl(SuggestionRepository suggestionRepository,
                                 ContractTemplateService contractTemplateService,
                                 ProfileServiceClient profileServiceClient,
                                 MessageService messageService,
                                 NotificationService notificationService,
                                 ScoringCommunicatorClient scoringCommunicatorClient) {

        this.suggestionRepository = suggestionRepository;
        this.contractTemplateService = contractTemplateService;
        this.profileServiceClient = profileServiceClient;
        this.messageService = messageService;
        this.notificationService = notificationService;
        this.scoringCommunicatorClient = scoringCommunicatorClient;
    }

    @Override
    public Suggestion save(Long contractTemplateId,
                           Long buyerId,
                           Long price,
                           String description) {

        ContractTemplate contractTemplate = contractTemplateService.find(contractTemplateId);
        return createNewSuggestion(contractTemplate, buyerId, price, description);
    }

    private Suggestion createNewSuggestion(ContractTemplate contractTemplate,
                                           Long buyerId,
                                           Long price,
                                           String description) {

        validateCreateNewSuggestion(contractTemplate, buyerId);

        Suggestion suggestion = new Suggestion();
        suggestion.setContractTemplate(contractTemplate);
        suggestion.setCreateDate(nowInTimestamp());
        suggestion.setUpdateDate(suggestion.getCreateDate());
        suggestion.setPrice(price);
        suggestion.setDescription(description);
        suggestion.setSuggestionState(SuggestionState.UNATTENDED);
        suggestion.setBuyerId(buyerId);
        suggestion.setPinned(Boolean.FALSE);

        messageService.send(
                contractTemplate.getSellerId(),
                MessageUtil.getMessage(CREATE_NEW_SUGGESTION_PUSH_MESSAGE, contractTemplate.getTitle()),
                contractTemplate.getId(),
                "CONTRACT_TEMPLATE",
                SubjectSubType.NEW_SUGGESTION);

        notificationService.send(
                contractTemplate.getSellerId(),
                MessageUtil.getMessage(CREATE_NEW_SUGGESTION_PUSH_MESSAGE, contractTemplate.getTitle()),
                contractTemplate.getId(),
                "CONTRACT_TEMPLATE");

        return save(suggestion);
    }

    @Override
    public Suggestion save(Suggestion suggestion) {
        return suggestionRepository.save(suggestion);
    }

    @Override
    public void update(Long suggestionId,
                       Long buyerId,
                       Long price,
                       String description) {

        Suggestion suggestion = findById(suggestionId);

        if (!buyerId.equals(suggestion.getBuyerId()))
            throw new IncompatibleValueException();

        if (suggestion.getSuggestionState().equals(SuggestionState.UNATTENDED)) {
            suggestion.setPrice(price);
            suggestion.setDescription(description);
            suggestion.setUpdateDate(nowInTimestamp());
            save(suggestion);

            messageService.send(
                    suggestion.getContractTemplate().getSellerId(),
                    MessageUtil.getMessage(CREATE_NEW_SUGGESTION_PUSH_MESSAGE, suggestion.getContractTemplate().getTitle()),
                    suggestion.getContractTemplate().getId(),
                    "CONTRACT_TEMPLATE",
                    SubjectSubType.NEW_SUGGESTION);

            notificationService.send(
                    suggestion.getContractTemplate().getSellerId(),
                    MessageUtil.getMessage(CREATE_NEW_SUGGESTION_PUSH_MESSAGE, suggestion.getContractTemplate().getTitle()),
                    suggestion.getContractTemplate().getId(),
                    "CONTRACT_TEMPLATE");

        } else if (suggestion.getSuggestionState().equals(SuggestionState.CONFIRMED)
                || suggestion.getSuggestionState().equals(SuggestionState.SIGNED)) {
            throw new SuggestionAlreadyConfirmedException();
        } else {
            createNewSuggestion(suggestion.getContractTemplate(), buyerId, price, description);
        }
    }

    @Override
    public Suggestion findById(Long id) {
        return suggestionRepository
                .findById(id)
                .orElseThrow(SuggestionNotFoundException::new);
    }

    @Override
    public List<SuggestionForSellerModel> readAllForSeller(Long contractTemplateId,
                                                           Long sellerId,
                                                           Integer page,
                                                           Integer pageSize,
                                                           Boolean asc,
                                                           SortBy sortBy) {

        List<SuggestionForSellerModel> responseList = new ArrayList<>();

        ContractTemplate contractTemplate = contractTemplateService.find(contractTemplateId);

        if (!contractTemplate.getSellerId().equals(sellerId)) {
            throw new SuggestionNotFoundException();
        }

        List<Suggestion> suggestions =
                suggestionRepository
                        .findAllByContractTemplateAndSuggestionStateNotIn(
                                contractTemplate,
                                List.of(SuggestionState.DENIED, SuggestionState.DELETED),
                                buildPageableForSuggestions(page, pageSize, asc, sortBy));

        Map<Long, Suggestion> suggestionMap =
                suggestions.stream().collect(Collectors.toMap(Suggestion::getBuyerId, suggestion -> suggestion));

        if (CollectionUtils.isEmpty(suggestions))
            return responseList;
        else {
            List<UserScore> usersScores = scoringCommunicatorClient.getUsersScores(
                    suggestions.stream().map(Suggestion::getBuyerId).collect(Collectors.toList()),
                    page,
                    pageSize,
                    true);
            Map<Long, Integer> userScoresMap =
                    usersScores.stream().collect(Collectors.toMap(UserScore::getUserId, UserScore::getScore));

            if (sortBy == SortBy.SCORE) {
                usersScores.forEach(userScore ->
                        responseList.add(buildSuggestionResponse(contractTemplate,
                                suggestionMap.get(userScore.getUserId()),
                                userScoresMap)));
            } else {
                suggestions.forEach(suggestion ->
                        responseList.add(buildSuggestionResponse(contractTemplate,
                                suggestion,
                                userScoresMap)));
            }
        }

        return responseList;
    }

    private SuggestionForSellerModel buildSuggestionResponse(ContractTemplate contractTemplate,
                                                             Suggestion suggestion,
                                                             Map<Long, Integer> userScoresMap) {
        if (contractTemplate.getNumberOfRepeats() == null || contractTemplate.getNumberOfRepeats() == 0) {

            Boolean enabled = suggestion.getSuggestionState().equals(SuggestionState.SIGNED)
                    || suggestion.getSuggestionState().equals(SuggestionState.CONFIRMED)
                    ? Boolean.FALSE
                    : Boolean.TRUE;

            User user = profileServiceClient.findById(suggestion.getBuyerId());
            user.setScore(userScoresMap.get(user.getUserId()));


            return new SuggestionForSellerModel(
                    suggestion.getId(),
                    suggestion.getCreateDate().getTime(),
                    suggestion.getUpdateDate() != null ? suggestion.getUpdateDate().getTime() : null,
                    suggestion.getPrice(),
                    suggestion.getDescription(),
                    suggestion.getPinned(),
                    suggestion.getSuggestionState(),
                    enabled,
                    user);

        } else {

            User user = profileServiceClient.findById(suggestion.getBuyerId());
            user.setScore(userScoresMap.get(user.getUserId()));

            return new SuggestionForSellerModel(
                    suggestion.getId(),
                    suggestion.getCreateDate().getTime(),
                    suggestion.getUpdateDate() == null ? null : suggestion.getUpdateDate().getTime(),
                    suggestion.getPrice(),
                    suggestion.getDescription(),
                    suggestion.getPinned(),
                    suggestion.getSuggestionState(),
                    Boolean.TRUE,
                    user);
        }
    }

    @Override
    public void pin(Long suggestionId,
                    Boolean toggle,
                    Long onlineUserId) {

        Suggestion suggestion = findById(suggestionId);

        if (!suggestion.getContractTemplate().getSellerId().equals(onlineUserId))
            throw new IncompatibleValueException();

        suggestion.setPinned(toggle);
        suggestion.setUpdateDate(nowInTimestamp());

        save(suggestion);
    }

    @Override
    public Suggestion confirm(Long suggestionId, Long contractTemplateId) {

        ContractTemplate contractTemplate = contractTemplateService.find(contractTemplateId);
        Suggestion suggestion = findById(suggestionId);

        validateConfirmSuggestion(suggestion, contractTemplate);

        suggestion.setSuggestionState(SuggestionState.CONFIRMED);
        suggestion.setSuggestionStateDate(nowInTimestamp());
        suggestion.setUpdateDate(nowInTimestamp());
        suggestionRepository.save(suggestion);

        return suggestion;
    }

    @Override
    public void deny(Long suggestionId, Long sellerId) {
        Suggestion suggestion = findById(suggestionId);

        if (!suggestion.getContractTemplate().getSellerId().equals(sellerId))
            throw new IncompatibleValueException();

        suggestion.setSuggestionState(SuggestionState.DENIED);
        suggestion.setSuggestionStateDate(nowInTimestamp());
        suggestion.setUpdateDate(nowInTimestamp());
        suggestionRepository.save(suggestion);

        messageService.send(
                suggestion.getBuyerId(),
                MessageUtil.getMessage(SUGGESTION_DENIED_PUSH_MESSAGE, suggestion.getContractTemplate().getTitle()),
                suggestion.getContractTemplate().getId(),
                "CONTRACT_TEMPLATE",
                SubjectSubType.SUGGESTION_DENIED);
    }

    @Override
    public void delete(Long suggestionId, Long onlineUserId) {
        Suggestion suggestion = findById(suggestionId);
        if(!Objects.equals(suggestion.getBuyerId(), onlineUserId) ||
                List.of(
                        SuggestionState.DENIED,
                        SuggestionState.CONFIRMED,
                        SuggestionState.SIGNED
                ).contains(suggestion.getSuggestionState())){
            throw new IncompatibleValueException();
        }
        suggestion.setSuggestionState(SuggestionState.DELETED);
        suggestion.setSuggestionStateDate(nowInTimestamp());
        suggestionRepository.save(suggestion);
    }

    @Override
    public BinaryDataHolder<User, User> getSides(Long suggestionId, Long onlineUserId) {
        Suggestion suggestion = findById(suggestionId);
        User buyer = profileServiceClient.findById(suggestion.getBuyerId());

        ContractTemplate contractTemplate = suggestion.getContractTemplate();
        User seller = profileServiceClient.findById(contractTemplate.getSellerId());

        if (onlineUserId.equals(buyer.getUserId()) || onlineUserId.equals(seller.getUserId()))
            return new BinaryDataHolder<>(seller, buyer);
        else
            throw new IncompatibleValueException();
    }

    @Override
    public Suggestion readSuggestionForBuyer(Long contractTemplateId, Long buyerId) {

        ContractTemplate contractTemplate = contractTemplateService.find(contractTemplateId);

        List<Suggestion> suggestions = readAllByContractTemplateAndBuyer(contractTemplate, buyerId);

        return suggestions
                .stream()
                .filter(suggestion ->
                        suggestion.getSuggestionState().equals(SuggestionState.UNATTENDED) &&
                                suggestion.getBuyerId().equals(buyerId))
                .findFirst()
                .or(() ->
                        suggestions.stream()
                                .filter(suggestion ->
                                        suggestion.getSuggestionState().equals(SuggestionState.DENIED) &&
                                                suggestion.getBuyerId().equals(buyerId))
                                .findFirst()
                )
                .orElseThrow(SuggestionNotFoundException::new);
    }

    @Override
    public Suggestion findLatestForBuyer(Long contractTemplateId, Long buyerId) {
        ContractTemplate contractTemplate = contractTemplateService.find(contractTemplateId);
        return suggestionRepository.findByContractTemplateAndBuyerIdOrderByUpdateDateDesc(contractTemplate, buyerId)
                .stream().findFirst().orElseThrow(SuggestionNotFoundException::new);
    }


    private void validateConfirmSuggestion(Suggestion suggestion, ContractTemplate contractTemplate) {

        if (!suggestion.getContractTemplate().getId().equals(contractTemplate.getId())
                || !suggestion.getSuggestionState().equals(SuggestionState.UNATTENDED))
            throw new CannotConfirmContractTemplate();

        if (contractTemplate.getNumberOfRepeats() < 0)
            throw new CannotConfirmContractTemplate();

    }

    private void validateCreateNewSuggestion(ContractTemplate contractTemplate, Long buyerId) {

        if (buyerId.equals(contractTemplate.getSellerId())) {
            log.debug("Buyer ID: {} - Seller ID: {}", buyerId, contractTemplate.getSellerId());
            throw new IncompatibleValueException();
        }

        List<Suggestion> suggestions = readAllByContractTemplateAndBuyer(contractTemplate, buyerId);

        if (suggestions.size() > 0) {
            log.debug(String.valueOf(suggestions.size()));
        }

        if (suggestions
                .stream()
                .anyMatch(suggestion ->
                        suggestion.getSuggestionState().equals(SuggestionState.UNATTENDED)
                                || suggestion.getSuggestionState().equals(SuggestionState.SIGNED)
                                || suggestion.getSuggestionState().equals(SuggestionState.CONFIRMED))) {

            throw new CannotCreateNewSuggestionException();
        }

        long acceptedSuggestionsCount = suggestionRepository
                .countByContractTemplateAndSuggestionStateIn(
                        contractTemplate,
                        List.of(SuggestionState.CONFIRMED,
                                SuggestionState.SIGNED));

        if (!contractTemplate.getRepeatable() && acceptedSuggestionsCount > 0)
            throw new ContractNumberOfRepeatsExceededException();

        if (contractTemplate.getRepeatable() && contractTemplate.getNumberOfRepeats() < 1)
            throw new ContractNumberOfRepeatsExceededException();

    }

    private List<Suggestion> readAllByContractTemplateAndBuyer(ContractTemplate contractTemplate, Long buyerId) {
        return suggestionRepository
                .findByContractTemplateAndBuyerIdOrderByCreateDateDesc(contractTemplate, buyerId);
    }

    private static Pageable buildPageableForSuggestions(Integer pageNumber,
                                                        Integer pageSize,
                                                        Boolean asc,
                                                        SortBy sortBy) {
        if (pageNumber < 1 || pageSize > 200)
            throw new BadPaginationException();

        if (sortBy == null) {
            sortBy = SortBy.NONE;
        }

        Sort sort;
        switch (sortBy) {
            case PRICE:
                sort = Sort.by("price", "updateDate");
                break;
            case SCORE:
            case UPDATE_DATE:
                sort = Sort.by("updateDate");
                break;
            case CREATE_DATE:
                sort = Sort.by("createDate", "updateDate");
                break;
            case PINNED:
            default:
                sort = Sort.by("pinned", "updateDate");
        }

        sort = asc ? sort.ascending() : sort.descending();
        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }

}