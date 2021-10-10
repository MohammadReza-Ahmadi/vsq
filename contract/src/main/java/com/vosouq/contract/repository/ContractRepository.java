package com.vosouq.contract.repository;

import com.vosouq.contract.model.Contract;
import com.vosouq.contract.model.ContractState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    List<Contract> findBySellerIdOrBuyerIdOrderByUpdateDateDesc(Long sellerId, Long buyerId);

    @Query("  select distinct c from contract c " +
            " where (c.sellerId = :sellerId or c.buyerId = :buyerId)" +
            " and c.state in :contractStates " +
            " order by c.updateDate desc")
    List<Contract> findBySellerIdOrBuyerIdAndStateInOrderByUpdateDateDesc(
            Long sellerId,
            Long buyerId,
            Collection<ContractState> contractStates,
            Pageable pageable);

    @Query("  select distinct c from contract c " +
            " where (c.sellerId = :sellerId or c.buyerId = :buyerId)" +
            " and c.state in :contractStates " +
            " and (:title is null or c.title like concat('%', :title, '%')) " +
            " order by c.updateDate desc")
    List<Contract> findBySellerIdOrBuyerIdAndTitleIsContainingAndStateInOrderByUpdateDateDesc(
            Long sellerId,
            Long buyerId,
            String title,
            Collection<ContractState> contractStates,
            Pageable pageable);

    List<Contract> findBySellerSignDateBeforeAndStateIs(Timestamp timestamp, ContractState contractState);

    List<Contract> findBySellerIdOrBuyerId(Long sellerId, Long buyerId, Pageable pageable);

    List<Contract> findByBuyerId(Long buyerId);

}
