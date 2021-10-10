package com.vosouq.contract.service;

import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.contract.model.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface ContractService {

    Contract confirmAndSaveCommodity(Long contractTemplateId,
                                     Long suggestionId,
                                     String title,
                                     Long onlineUserId,
                                     Long price,
                                     Long terminationPenalty,
                                     Timestamp deliveryDate,
                                     String province,
                                     String city,
                                     String neighbourhood,
                                     String description,
                                     List<PaymentTemplateModel> paymentTemplates,
                                     List<Long> fileIds,
                                     Long pricePerUnit,
                                     Integer quantity,
                                     Unit unit,
                                     List<Long> otherAttachments,
                                     String signDescription);

    Contract confirmAndSaveVehicle(Long contractTemplateId,
                                   Long suggestionId,
                                   String title,
                                   Long sellerId,
                                   Long price,
                                   Long terminationPenalty,
                                   Timestamp deliveryDate,
                                   String province,
                                   String city,
                                   String neighbourhood,
                                   String description,
                                   List<PaymentTemplateModel> paymentTemplates,
                                   List<Long> fileIds,
                                   Integer manufactureYear,
                                   Integer usage,
                                   Gearbox gearbox,
                                   Fuel fuel,
                                   BodyStatus bodyStatus,
                                   Color color,
                                   VehicleAttachmentModel vehicleAttachmentModel,
                                   Boolean isOwner,
                                   List<Long> vehicleAttorneyAttachments,
                                   List<Long> otherAttachments,
                                   String signDescription);

    Contract confirmAndSaveService(Long contractTemplateId,
                                   Long suggestionId,
                                   String title,
                                   Long sellerId,
                                   Long price,
                                   Long terminationPenalty,
                                   Timestamp deliveryDate,
                                   String province,
                                   String city,
                                   String neighbourhood,
                                   String description,
                                   List<PaymentTemplateModel> paymentTemplates,
                                   List<Long> fileIds,
                                   List<Long> otherAttachments,
                                   String signDescription);

    Contract findById(Long id);

    BinaryDataHolder<User, User> getContractSides(Long contractId);

    List<Contract> findAll(Long userId);

    List<RecentDealModel> getRecentDeals(Long userId, Integer page, Integer pageSize);

    List<RecentDealModel> getRecentDeals(Long userId, List<ActionState> filters, Integer page, Integer pageSize);

    List<ContractModel> findAllContractModels(
            Long userId,
            String term,
            Collection<ContractState> states,
            Integer page,
            Integer pageSize);

    List<ContractModel> findAllContractModels(Long userId,
                                              Collection<ContractState> states,
                                              Integer page,
                                              Integer pageSize);

    List<ContractModel> findAllTurnovers(Long userId);

    Timestamp signContractByBuyer(Long contractId, Long currentUserId);

    void rejectContractByBuyer(Long contractId, Long usreId);

    ContractStatusModel getContractStatus(Long contractId, Long userId);

    void doPayment(Long contractId, Long amount);

    long paymentInquiry(Long userId, Long contractId);

    void depositContractFee(Long contractId,
                            Boolean success,
                            Long depositDate);

    void deliverContract(Long contractId, Long buyerId);

    // todo : just for testing purposes!! should be completed Later after Analysis
    void sealOfApprovalByBuyer(Long contractId,
                               Long buyerId,
                               Boolean isApproved);
}
