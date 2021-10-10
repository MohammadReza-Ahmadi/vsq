package com.vosouq.contract.repository;

import com.vosouq.contract.model.ContractTemplate;
import com.vosouq.contract.model.FileAddress;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractTemplateRepository extends JpaRepository<ContractTemplate, Long> {

    @Query("  select distinct ct from contract_template ct " +
            " left join Suggestion s on ct.id = s.contractTemplate.id " +
            " where " +
            " ct.numberOfRepeats > :numberOfRepeats " +
            " and (ct.sellerId = :userId " +
            " or (s.buyerId = :userId " +
            " and s.suggestionState <> 'CONFIRMED' and s.suggestionState <> 'SIGNED')) " +
            " order by ct.createDate desc ")
    List<ContractTemplate> findAllByUserId(Long userId, Integer numberOfRepeats, Pageable pageable);

    List<ContractTemplate> findAllBySellerIdAndNumberOfRepeatsGreaterThan(Long sellerId, Integer numberOfRepeats, Pageable pageable);

    Optional<ContractTemplate> findById(Long id);

    Optional<ContractTemplate> findByIdAndSellerId(Long id, Long sellerId);

    @Query("select ct.files from contract_template ct where ct.id = :contractTemplateId")
    List<FileAddress> findAllFilesByContractTemplateId(Long contractTemplateId);

}
