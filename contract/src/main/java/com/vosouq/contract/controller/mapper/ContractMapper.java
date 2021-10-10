package com.vosouq.contract.controller.mapper;

import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.model.*;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

import static com.vosouq.contract.utills.TimeUtil.nowInMillis;

public class ContractMapper {

    public static GetAllTurnoversResponse mapToTurnover(ContractModel contractModel) {
        GetAllTurnoversResponse response = new GetAllTurnoversResponse();

        response.setId(contractModel.getId());
        response.setImageFile(contractModel.getImageFile());
        response.setPrice(contractModel.getPrice());
        response.setSideName(contractModel.getActorName());
        response.setTitle(contractModel.getTitle());
        response.setUpdateDate(contractModel.getUpdateDate());
        response.setSide(contractModel.getActor().equals(Actor.BUYER) ? Side.BUYING : Side.SELLING);
        return response;
    }

    public static GetAllContractsResponse map(ContractModel contractModel) {
        GetAllContractsResponse response = new GetAllContractsResponse();

        response.setId(contractModel.getId());
        response.setImageFile(contractModel.getImageFile());
        response.setPrice(contractModel.getPrice());
        response.setSideName(contractModel.getActorName());
        response.setState(contractModel.getState());
        response.setTitle(contractModel.getTitle());
        response.setUpdateDate(contractModel.getUpdateDate());
        response.setSide(contractModel.getActor().equals(Actor.BUYER) ? Side.BUYING : Side.SELLING);
        return response;
    }

    public static GetContractResponse map(Contract contract) {
        GetContractResponse response = null;
        if (contract instanceof CommodityContract) {
            response = setCommodity((CommodityContract) contract);
        } else if (contract instanceof VehicleContract) {
            response = setVehicle((VehicleContract) contract);
        } else if (contract instanceof ServiceContract) {
            response = new GetServiceContractResponse();
            response.setContractType(ContractType.SERVICE);
        }
        setContract(contract, response);
        return response;
    }

    public static ContractStatusResponse map(ContractStatusModel status) {
        ContractStatusResponse response = new ContractStatusResponse();
        if (status != null) {
            response.setContractId(status.getContractId());
            response.setNow(nowInMillis());
            response.setCurrentActionDueDate(status.getCurrentActionDueDate() != null ? status.getCurrentActionDueDate().getTime() : null);
            response.setAction(status.getAction());
            response.setMessages(status.getMessages());
            response.setTimerMessage(status.getTimerMessage());

            response.setSteps(
                    status.getSteps()
                            .stream()
                            .map(contractStepModel -> map(contractStepModel, status.getSellerId(), status.getBuyerId()))
                            .collect(Collectors.toList()));
        }
        return response;
    }

    public static VehicleAttachmentModel map(ConfirmVehicleAttachmentRequest request) {
        VehicleAttachmentModel model = new VehicleAttachmentModel();
        if (request != null) {
            model.setBarcodeNumber(request.getBarcodeNumber());
            model.setChassisNumber(request.getChassisNumber());
            model.setEngineNumber(request.getEngineNumber());
            model.setCardFront(request.getCardFront());
            model.setCardBack(request.getCardBack());
            model.setDocument(request.getDocument());
        }
        return model;
    }

    private static ContractStepResponse map(ContractStepModel step, Long sellerId, Long buyerId) {
        ContractStepResponse response = new ContractStepResponse();
        if (step != null) {
            response.setTitle(step.getTitle());

            response.setLeftImage("profile/users/" + buyerId + "/image");
            response.setRightImage("profile/users/" + sellerId + "/image");

            if (step.getLeft().equals(Actor.VOSOUQ)) {
                response.setLeftImage("VSQ");
            }

            if (step.getRight().equals(Actor.VOSOUQ)) {
                response.setRightImage("VSQ");
            }

            if (step.getActiveActor().equals(Actor.BUYER)) {
                response.setActiveActor(ActiveActor.LEFT);
            } else if (step.getActiveActor().equals(Actor.SELLER)) {
                response.setActiveActor(ActiveActor.RIGHT);
            } else if (step.getActiveActor().equals(Actor.VOSOUQ)) {
                if (step.getRight().equals(Actor.VOSOUQ))
                    response.setActiveActor(ActiveActor.RIGHT);
                else
                    response.setActiveActor(ActiveActor.LEFT);
            } else {
                response.setActiveActor(ActiveActor.NONE);
                response.setLeftImage("");
                response.setRightImage("");
            }

            response.setAmount(step.getAmount());
            response.setDueDate(step.getDueDate() != null ? step.getDueDate().getTime() : null);
            response.setActionDate(step.getActionDate() != null ? step.getActionDate().getTime() : null);
            response.setStepStatus(step.getStepStatus());
        }
        return response;
    }

    private static void setContract(Contract contract, GetContractResponse response) {
        if (contract != null) {
            response.setId(contract.getId());
            response.setCreateDate(contract.getCreateDate() != null ? contract.getCreateDate().getTime() : null);
            response.setUpdateDate(contract.getUpdateDate() == null ? null : contract.getUpdateDate().getTime());
            response.setState(contract.getState());
            response.setTitle(contract.getTitle());
            response.setPrice(contract.getPrice());
            response.setTerminationPenalty(contract.getTerminationPenalty());
            if (contract.getDelivery() != null && contract.getDelivery().getDueDate() != null) {
                response.setDeliveryDate(contract.getDelivery().getDueDate().getTime());
            }
            response.setProvince(contract.getProvince());
            response.setCity(contract.getCity());
            response.setNeighbourhood(contract.getNeighbourhood());
            response.setDescription(contract.getDescription());
            response.setMultiStepPayment(contract.getMultiStepPayment());
            response.setSellerSignDate(contract.getSellerSignDate() != null ? contract.getSellerSignDate().getTime(): null);
            response.setBuyerSignDate(contract.getBuyerSignDate() != null ? contract.getBuyerSignDate().getTime() : null);
            response.setPayments(PaymentMapper.map(contract.getPayments()));
            response.setFileIds(FileAddressMapper.map(contract.getFiles()));
            response.setSignDescription(contract.getSignDescription());

            if (!CollectionUtils.isEmpty(contract.getOtherAttachments()))
                response.setOtherAttachments(
                        contract.getOtherAttachments()
                                .stream()
                                .map(FileAddress::getId)
                                .collect(Collectors.toList()));
        }
    }

    private static GetCommodityContractResponse setCommodity(CommodityContract contract) {

        GetCommodityContractResponse response = new GetCommodityContractResponse();

        response.setContractType(ContractType.COMMODITY);
        response.setPricePerUnit(contract.getPricePerUnit());
        response.setQuantity(contract.getQuantity());
        response.setUnit(contract.getUnit());

        return response;
    }

    private static GetVehicleContractResponse setVehicle(VehicleContract contract) {

        GetVehicleContractResponse response = new GetVehicleContractResponse();

        response.setContractType(ContractType.VEHICLE);
        response.setManufactureYear(contract.getManufactureYear());
        response.setUsage(contract.getUsage());
        response.setFuel(contract.getFuel());
        response.setGearbox(contract.getGearbox());
        response.setBodyStatus(contract.getBodyStatus());
        response.setColor(contract.getColor());

        if (contract.getVehicleAttachment() != null) {
            VehicleAttachmentResponse vehicleAttachmentResponse = new VehicleAttachmentResponse();
            vehicleAttachmentResponse.setEngineNumber(contract.getVehicleAttachment().getEngineNumber());
            vehicleAttachmentResponse.setBarcodeNumber(contract.getVehicleAttachment().getBarcodeNumber());
            vehicleAttachmentResponse.setChassisNumber(contract.getVehicleAttachment().getChassisNumber());
            vehicleAttachmentResponse.setCardFront(contract.getVehicleAttachment().getCardFront().getId());
            vehicleAttachmentResponse.setCardBack(contract.getVehicleAttachment().getCardBack().getId());
            vehicleAttachmentResponse.setDocument(contract.getVehicleAttachment().getDocument().getId());

            response.setVehicleAttachment(vehicleAttachmentResponse);
        }

        response.setIsOwner(contract.getIsOwner());

        if (!CollectionUtils.isEmpty(contract.getAttorneyAttachments()))
            response.setVehicleAttorneyAttachments(
                    contract.getAttorneyAttachments()
                            .stream()
                            .map(FileAddress::getId)
                            .collect(Collectors.toList()));

        return response;

    }

}
