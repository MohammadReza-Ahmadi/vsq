package com.vosouq.contract.service.impl;

import com.vosouq.commons.model.OnlineUser;
import com.vosouq.contract.exception.ContractNotFoundException;
import com.vosouq.contract.exception.ContractTemplateNotFoundException;
import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.model.*;
import com.vosouq.contract.repository.CommodityContractTemplateRepository;
import com.vosouq.contract.repository.ContractTemplateRepository;
import com.vosouq.contract.repository.ServiceContractTemplateRepository;
import com.vosouq.contract.repository.VehicleContractTemplateRepository;
import com.vosouq.contract.service.ContractTemplateService;
import com.vosouq.contract.service.FileAddressService;
import com.vosouq.contract.service.feign.ProfileServiceClient;
import com.vosouq.contract.utills.PaginationUtil;
import com.vosouq.contract.utills.SortBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.vosouq.contract.utills.TimeUtil.nowInTimestamp;

@Service
@Transactional
public class ContractTemplateServiceImpl implements ContractTemplateService {

    private final ContractTemplateRepository contractTemplateRepository;
    private final CommodityContractTemplateRepository commodityContractTemplateRepository;
    private final VehicleContractTemplateRepository vehicleContractTemplateRepository;
    private final ServiceContractTemplateRepository serviceContractTemplateRepository;
    private final FileAddressService fileAddressService;
    private final ProfileServiceClient profileServiceClient;
    private final OnlineUser onlineUser;

    @Autowired
    public ContractTemplateServiceImpl(ContractTemplateRepository contractTemplateRepository,
                                       CommodityContractTemplateRepository commodityContractTemplateRepository,
                                       VehicleContractTemplateRepository vehicleContractTemplateRepository,
                                       ServiceContractTemplateRepository serviceContractTemplateRepository,
                                       FileAddressService fileAddressService,
                                       ProfileServiceClient profileServiceClient,
                                       @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                               OnlineUser onlineUser) {

        this.contractTemplateRepository = contractTemplateRepository;
        this.commodityContractTemplateRepository = commodityContractTemplateRepository;
        this.vehicleContractTemplateRepository = vehicleContractTemplateRepository;
        this.serviceContractTemplateRepository = serviceContractTemplateRepository;
        this.fileAddressService = fileAddressService;
        this.profileServiceClient = profileServiceClient;
        this.onlineUser = onlineUser;
    }

    @Override
    public List<ContractTemplateModel> getAll(Long userId,
                                              Integer page,
                                              Integer pageSize,
                                              Boolean asc,
                                              SortBy sortBy) {

        Pageable pageable = PaginationUtil.buildPageable(page, pageSize, asc, sortBy);

        List<ContractTemplate> contractTemplates = contractTemplateRepository.findAllByUserId(userId,
                0,
                pageable);

        return contractTemplates
                .stream()
                .map(contractTemplate -> {

                    ContractTemplateModel contractTemplateModel = new ContractTemplateModel(
                            contractTemplate.getId(),
                            contractTemplate.getSellerId(),
                            contractTemplate.getCreateDate(),
                            contractTemplate.getTitle(),
                            contractTemplate.getPrice(),
                            contractTemplate.getFavorite(),
                            contractTemplate.getRepeatable(),
                            contractTemplate.getViewable(),
                            contractTemplate.getNumberOfRepeats());

                    if (contractTemplateModel.getSellerId().equals(userId)) {
                        contractTemplateModel.setActor(Actor.SELLER);
                    } else {
                        contractTemplateModel.setActor(Actor.BUYER);
                        User user = profileServiceClient.findById(contractTemplateModel.getSellerId());
                        contractTemplateModel.setSellerName(user.getName());
                        contractTemplateModel.setFavorite(null);
                        contractTemplateModel.setRepeatable(null);
                        contractTemplateModel.setViewable(null);
                    }

                    contractTemplateModel.setFileId(
                            contractTemplateRepository
                                    .findAllFilesByContractTemplateId(contractTemplate.getId())
                                    .stream()
                                    .filter(fileAddress -> fileAddress.getFileType().equals(FileType.PRODUCT_IMAGE))
                                    .map(FileAddress::getId)
                                    .findFirst()
                                    .orElse(null));

                    return contractTemplateModel;
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<ContractTemplateModel> getAllSellerContracts(Long sellerId,
                                                             Integer page,
                                                             Integer pageSize,
                                                             Boolean asc,
                                                             SortBy sortBy) {

        Pageable pageable = PaginationUtil.buildPageable(page, pageSize, asc, sortBy);

        List<ContractTemplate> contractTemplates =
                contractTemplateRepository.findAllBySellerIdAndNumberOfRepeatsGreaterThan(sellerId,
                        0,
                        pageable);

        return contractTemplates
                .stream()
                .map(contractTemplate ->
                        new ContractTemplateModel(
                                contractTemplate.getId(),
                                contractTemplate.getSellerId(),
                                null,
                                contractTemplate.getCreateDate(),
                                contractTemplate.getTitle(),
                                contractTemplate.getPrice(),
                                null,
                                null,
                                null,
                                Actor.BUYER,
                                contractTemplateRepository
                                        .findAllFilesByContractTemplateId(contractTemplate.getId())
                                        .stream()
                                        .filter(fileAddress -> fileAddress.getFileType().equals(FileType.PRODUCT_IMAGE))
                                        .map(FileAddress::getId)
                                        .findFirst()
                                        .orElse(null),
                                contractTemplate.getNumberOfRepeats()))
                .collect(Collectors.toList());
    }

    @Override
    public ContractTemplate find(Long id) {
        return contractTemplateRepository
                .findById(id)
                .orElseThrow(ContractTemplateNotFoundException::new);
    }

    private ContractTemplate find(Long id, Long sellerId) {
        return contractTemplateRepository
                .findByIdAndSellerId(id, sellerId)
                .orElseThrow(ContractTemplateNotFoundException::new);
    }

    @Override
    public CommodityContractTemplate saveCommodity(CommodityContractTemplate commodityContractTemplate) {
        verifyPrice(commodityContractTemplate);
        validateRepeatableTemplate(commodityContractTemplate);
        commodityContractTemplate.setCreateDate(nowInTimestamp());
        commodityContractTemplate.setFavorite(Boolean.FALSE);
        loadFiles(commodityContractTemplate);

        return commodityContractTemplateRepository.save(commodityContractTemplate);
    }

    @Override
    public CommodityContractTemplate updateCommodity(CommodityContractTemplate commodityContractTemplate) {
        verifyPrice(commodityContractTemplate);
        CommodityContractTemplate foundContractTemplate =
                commodityContractTemplateRepository
                        .findById(commodityContractTemplate.getId())
                        .orElseThrow(ContractNotFoundException::new);
        if (!Objects.equals(onlineUser.getUserId(), foundContractTemplate.getSellerId())) {
            throw new IncompatibleValueException();
        }
        validateRepeatableTemplate(commodityContractTemplate);
        commodityContractTemplate.setUpdateDate(nowInTimestamp());
        commodityContractTemplate.setCreateDate(foundContractTemplate.getCreateDate());
        commodityContractTemplate.setSellerId(foundContractTemplate.getSellerId());
        loadFiles(commodityContractTemplate);

        return commodityContractTemplateRepository.save(commodityContractTemplate);
    }

    @Override
    public VehicleContractTemplate saveVehicle(VehicleContractTemplate vehicleContractTemplate) {
        verifyPrice(vehicleContractTemplate);
        validateRepeatableTemplate(vehicleContractTemplate);
        vehicleContractTemplate.setCreateDate(nowInTimestamp());
        vehicleContractTemplate.setFavorite(Boolean.FALSE);
        loadFiles(vehicleContractTemplate);

        return vehicleContractTemplateRepository.save(vehicleContractTemplate);
    }

    @Override
    public VehicleContractTemplate updateVehicle(VehicleContractTemplate vehicleContractTemplate) {
        verifyPrice(vehicleContractTemplate);
        VehicleContractTemplate foundContractTemplate =
                vehicleContractTemplateRepository
                        .findById(vehicleContractTemplate.getId())
                        .orElseThrow(ContractNotFoundException::new);
        if (!Objects.equals(onlineUser.getUserId(), (foundContractTemplate.getSellerId()))) {
            throw new IncompatibleValueException();
        }
        validateRepeatableTemplate(vehicleContractTemplate);
        vehicleContractTemplate.setUpdateDate(nowInTimestamp());
        vehicleContractTemplate.setCreateDate(foundContractTemplate.getCreateDate());
        vehicleContractTemplate.setSellerId(foundContractTemplate.getSellerId());
        loadFiles(vehicleContractTemplate);
        return vehicleContractTemplateRepository.save(vehicleContractTemplate);
    }

    @Override
    public ServiceContractTemplate saveService(ServiceContractTemplate serviceContractTemplate) {
        validateRepeatableTemplate(serviceContractTemplate);
        serviceContractTemplate.setCreateDate(nowInTimestamp());
        serviceContractTemplate.setFavorite(Boolean.FALSE);
        loadFiles(serviceContractTemplate);
        return serviceContractTemplateRepository.save(serviceContractTemplate);
    }

    @Override
    public ServiceContractTemplate updateService(ServiceContractTemplate serviceContractTemplate) {

        ServiceContractTemplate foundContractTemplate =
                serviceContractTemplateRepository
                        .findById(serviceContractTemplate.getId())
                        .orElseThrow(ContractNotFoundException::new);
        if (!Objects.equals(onlineUser.getUserId(), (foundContractTemplate.getSellerId()))) {
            throw new IncompatibleValueException();
        }
        validateRepeatableTemplate(serviceContractTemplate);
        serviceContractTemplate.setUpdateDate(nowInTimestamp());
        serviceContractTemplate.setCreateDate(foundContractTemplate.getCreateDate());
        serviceContractTemplate.setSellerId(foundContractTemplate.getSellerId());
        loadFiles(serviceContractTemplate);
        return serviceContractTemplateRepository.save(serviceContractTemplate);
    }

    @Override
    public void minusNumberOfRepeats(ContractTemplate contractTemplate) {
        contractTemplate.setNumberOfRepeats(contractTemplate.getNumberOfRepeats() - 1);
        contractTemplateRepository.save(contractTemplate);
    }

    @Override
    public void updateFavoriteStatus(Long contractTemplateId, Long currentUserId, Boolean favorite) {
        ContractTemplate contractTemplate = find(contractTemplateId, currentUserId);
        contractTemplate.setFavorite(favorite);
        contractTemplateRepository.save(contractTemplate);
    }

    @Override
    public ContractTemplate updateViewableAndRepeatable(Long contractTemplateId,
                                                        Boolean viewable,
                                                        Boolean repeatable,
                                                        Integer numberOfRepeats) {
        ContractTemplate contractTemplate =
                contractTemplateRepository.findById(contractTemplateId).orElseThrow(ContractNotFoundException::new);
        if (!Objects.equals(onlineUser.getUserId(), contractTemplate.getSellerId())) {
            throw new IncompatibleValueException();
        }
        contractTemplate.setViewable(viewable);
        contractTemplate.setRepeatable(repeatable);
        contractTemplate.setNumberOfRepeats(numberOfRepeats);
        validateRepeatableTemplate(contractTemplate);
        return contractTemplateRepository.save(contractTemplate);
    }

    private void validateRepeatableTemplate(ContractTemplate contractTemplate) {
        if (contractTemplate.getRepeatable()) {
            if (contractTemplate.getNumberOfRepeats() == null || contractTemplate.getNumberOfRepeats() < 2) {
                throw new IncompatibleValueException();
            }
        } else {
            contractTemplate.setNumberOfRepeats(1);
        }
    }

    private void loadFiles(ContractTemplate contractTemplate) {
        if (!CollectionUtils.isEmpty(contractTemplate.getFiles()))
            contractTemplate.getFiles().forEach(fileAddress -> {
                FileAddress foundFileAddress =
                        fileAddressService.findAndValidate(
                                fileAddress.getId(),
                                FileType.PRODUCT_IMAGE,
                                contractTemplate.getSellerId());
                fileAddress.setAddress(foundFileAddress.getAddress());
                fileAddress.setCreateDate(foundFileAddress.getCreateDate());
                fileAddress.setUpdateDate(foundFileAddress.getUpdateDate());
                fileAddress.setFileFormat(foundFileAddress.getFileFormat());
                fileAddress.setFileType(foundFileAddress.getFileType());
                fileAddress.setOwner(foundFileAddress.getOwner());
            });
        else
            contractTemplate.setFiles(null);
    }

    private <T extends ContractTemplate> void verifyPrice(T contractTemplate) {
        if (contractTemplate.getPrice() == null) {
            throw new IncompatibleValueException();
        }
    }

}
