package com.vosouq.contract.controller.mapper;

import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class ContractTemplateMapper {

    public static GetContractTemplateResponse map(Long sellerId, ContractTemplate contractTemplate) {

        GetContractTemplateResponse response;

        if (contractTemplate instanceof VehicleContractTemplate) {
            response = setVehicleProperties((VehicleContractTemplate) contractTemplate);
            response.setContractType(ContractType.VEHICLE);
        } else if (contractTemplate instanceof CommodityContractTemplate) {
            response = setCommodityProperties((CommodityContractTemplate) contractTemplate);
            response.setContractType(ContractType.COMMODITY);
        } else {
            response = new ServiceContractTemplateResponse();
            response.setContractType(ContractType.SERVICE);
        }

        response.setTitle(contractTemplate.getTitle());
        response.setPrice(contractTemplate.getPrice());
        response.setProvince(contractTemplate.getProvince());
        response.setCity(contractTemplate.getCity());
        response.setNeighbourhood(contractTemplate.getNeighbourhood());
        response.setDescription(contractTemplate.getDescription());
        if (contractTemplate.getFiles() != null) {
            response.setFileIds(FileAddressMapper.map(contractTemplate.getFiles()));
        }

        if (sellerId.equals(contractTemplate.getSellerId())) {
            response.setRepeatable(contractTemplate.getRepeatable());
            response.setNumberOfRepeats(contractTemplate.getNumberOfRepeats());
            response.setViewable(contractTemplate.getViewable());
            response.setFavorite(contractTemplate.getFavorite());
            response.setCreateDate(contractTemplate.getCreateDate().getTime());
            response.setUpdateDate(contractTemplate.getUpdateDate() != null
                    ? contractTemplate.getUpdateDate().getTime() : null);
        }

        return response;
    }

    public static List<GetAllContractTemplatesResponse> map(List<ContractTemplateModel> contractTemplateModels) {
        return contractTemplateModels
                .stream()
                .map(contractTemplateModel ->
                        new GetAllContractTemplatesResponse(
                                contractTemplateModel.getId(),
                                contractTemplateModel.getCreateDate().getTime(),
                                contractTemplateModel.getTitle(),
                                contractTemplateModel.getPrice(),
                                contractTemplateModel.getFileId(),
                                contractTemplateModel.getActor().equals(Actor.BUYER) ? Side.BUYING : Side.SELLING,
                                contractTemplateModel.getSellerName(),
                                contractTemplateModel.getFavorite(),
                                contractTemplateModel.getRepeatable(),
                                contractTemplateModel.getViewable(),
                                contractTemplateModel.getNumberOfRepeats()))
                .collect(Collectors.toList());
    }

    private static VehicleContractTemplateResponse setVehicleProperties(VehicleContractTemplate contractTemplate) {

        VehicleContractTemplateResponse response = new VehicleContractTemplateResponse();

        response.setContractType(ContractType.VEHICLE);
        response.setManufactureYear(contractTemplate.getManufactureYear());
        response.setUsage(contractTemplate.getUsage());
        response.setGearbox(contractTemplate.getGearbox());
        response.setFuel(contractTemplate.getFuel());
        response.setBodyStatus(contractTemplate.getBodyStatus());
        response.setColor(contractTemplate.getColor());
        response.setIsOwner(contractTemplate.getIsOwner());

        return response;
    }

    private static CommodityContractTemplateResponse setCommodityProperties(CommodityContractTemplate contractTemplate) {

        CommodityContractTemplateResponse response = new CommodityContractTemplateResponse();

        response.setContractType(ContractType.COMMODITY);
        response.setPricePerUnit(contractTemplate.getPricePerUnit());
        response.setQuantity(contractTemplate.getQuantity());
        response.setUnit(contractTemplate.getUnit());

        return response;
    }

}