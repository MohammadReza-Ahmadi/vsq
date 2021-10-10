package com.vosouq.contract.repository;

import com.vosouq.contract.model.ContractTemplate;
import com.vosouq.contract.model.Suggestion;
import com.vosouq.contract.model.SuggestionState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    List<Suggestion> findAllByContractTemplateAndSuggestionStateNotIn(ContractTemplate contractTemplate,
                                                                                     List<SuggestionState> actions,
                                                                                     Pageable pageable);

    List<Suggestion> findByContractTemplateAndBuyerIdOrderByCreateDateDesc(ContractTemplate contractTemplate, Long buyerId);

    long countByContractTemplateAndSuggestionStateIn(ContractTemplate contractTemplate,
                                                                    List<SuggestionState> states);
    List<Suggestion> findByContractTemplateAndBuyerIdOrderByUpdateDateDesc(ContractTemplate contractTemplate,
                                                                           Long buyerId);
}