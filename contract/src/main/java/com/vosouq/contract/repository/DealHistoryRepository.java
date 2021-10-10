package com.vosouq.contract.repository;

import com.vosouq.contract.model.ActionState;
import com.vosouq.contract.model.DealHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DealHistoryRepository extends
        JpaRepository<DealHistory, Long> {

    List<DealHistory> findBySellerIdOrBuyerIdOrderByUpdateDateDesc(Long sellerId, Long buyerId, Pageable pageable);

    @Query("select dealHistory from DealHistory dealHistory where dealHistory.actionState in :filters " +
            "and (dealHistory.buyerId = :buyerId or dealHistory.sellerId = :sellerId)")
    List<DealHistory> findByActionStateInAndSellerIdOrBuyerId(@Param("filters") List<ActionState> filters,
                                                              @Param("sellerId") Long sellerId,
                                                              @Param("buyerId") Long buyerId,
                                                              Pageable pageable);

    Optional<DealHistory> findFirstBySubjectIdOrderByActionDateDesc(Long subjectId);



}
