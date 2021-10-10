package com.vosouq.contract.service;

import com.vosouq.contract.model.*;
import com.vosouq.contract.utills.SortBy;

import java.util.List;

public interface ContractTemplateService {

    List<ContractTemplateModel> getAll(Long userId,
                                       Integer page,
                                       Integer pageSize,
                                       Boolean asc,
                                       SortBy sortBy);

    List<ContractTemplateModel> getAllSellerContracts(Long sellerId,
                                                      Integer page,
                                                      Integer pageSize,
                                                      Boolean asc,
                                                      SortBy sortBy);

    ContractTemplate find(Long id);

    CommodityContractTemplate saveCommodity(CommodityContractTemplate commodityContractTemplate);
    CommodityContractTemplate updateCommodity(CommodityContractTemplate commodityContractTemplate);

    VehicleContractTemplate saveVehicle(VehicleContractTemplate vehicleContractTemplate);
    VehicleContractTemplate updateVehicle(VehicleContractTemplate vehicleContractTemplate);

    ServiceContractTemplate saveService(ServiceContractTemplate serviceContractTemplate);
    ServiceContractTemplate updateService(ServiceContractTemplate serviceContractTemplate);

    void minusNumberOfRepeats(ContractTemplate contractTemplate);

    void updateFavoriteStatus(Long contractTemplateId, Long currentUserId, Boolean favorite);
    ContractTemplate updateViewableAndRepeatable(Long contractTemplateId,
                                     Boolean viewable,
                                     Boolean repeatable,
                                     Integer numberOfRepeats);
}
