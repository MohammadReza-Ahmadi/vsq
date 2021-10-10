package com.vosouq.contract.controller;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.controller.mapper.ContractTemplateMapper;
import com.vosouq.contract.controller.mapper.MetaDataMapper;
import com.vosouq.contract.model.*;
import com.vosouq.contract.service.ContractTemplateService;
import com.vosouq.contract.service.ProfileService;
import com.vosouq.contract.service.SuggestionService;
import com.vosouq.contract.utills.SortBy;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@VosouqRestController
@RequestMapping("/templates")
public class ContractTemplateController {

    private final ContractTemplateService contractTemplateService;
    private final SuggestionService suggestionService;
    private final ProfileService profileService;
    private final OnlineUser onlineUser;
    private final ModelMapper modelMapper;

    public ContractTemplateController(ContractTemplateService contractTemplateService,
                                      SuggestionService suggestionService, ProfileService profileService,
                                      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                              OnlineUser onlineUser,
                                      ModelMapper modelMapper) {

        this.contractTemplateService = contractTemplateService;
        this.suggestionService = suggestionService;
        this.profileService = profileService;
        this.onlineUser = onlineUser;
        this.modelMapper = modelMapper;
        Converter<List<Long>, List<FileAddress>> converter = new AbstractConverter<>() {
            @Override
            protected List<FileAddress> convert(List<Long> source) {
                return source.stream()
                        .map(FileAddress::new)
                        .collect(Collectors.toList());
            }
        };
        modelMapper.createTypeMap(CreateServiceContractTemplateRequest.class, ServiceContractTemplate.class)
                .addMappings(mapper ->
                        mapper.using(converter)
                                .map(CreateServiceContractTemplateRequest::getFileIds, ServiceContractTemplate::setFiles));
        modelMapper.createTypeMap(CreateCommodityContractTemplateRequest.class, CommodityContractTemplate.class)
                .addMappings(mapper ->
                        mapper.using(converter)
                                .map(CreateCommodityContractTemplateRequest::getFileIds, CommodityContractTemplate::setFiles));
        modelMapper.createTypeMap(CreateVehicleContractTemplateRequest.class, VehicleContractTemplate.class)
                .addMappings(mapper ->
                        mapper.using(converter)
                                .map(CreateVehicleContractTemplateRequest::getFileIds, VehicleContractTemplate::setFiles));
        modelMapper.createTypeMap(UpdateServiceContractTemplateRequest.class, ServiceContractTemplate.class)
                .addMappings(mapper ->
                        mapper.using(converter)
                                .map(UpdateServiceContractTemplateRequest::getFileIds, ServiceContractTemplate::setFiles));
        modelMapper.createTypeMap(UpdateCommodityContractTemplateRequest.class, CommodityContractTemplate.class)
                .addMappings(mapper ->
                        mapper.using(converter)
                                .map(UpdateCommodityContractTemplateRequest::getFileIds, CommodityContractTemplate::setFiles));
        modelMapper.createTypeMap(UpdateVehicleContractTemplateRequest.class, VehicleContractTemplate.class)
                .addMappings(mapper ->
                        mapper.using(converter)
                                .map(UpdateVehicleContractTemplateRequest::getFileIds, VehicleContractTemplate::setFiles));
    }

    @GetMapping("/seller/{sellerId}")
    public List<GetAllContractTemplatesResponse> getAllTemplatesOfSeller(
            @PathVariable @Valid Long sellerId,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "25") @Max(200) @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "false") Boolean asc,
            @RequestParam(required = false, defaultValue = "CREATE_DATE") SortBy sortBy) {

        return ContractTemplateMapper.map(
                contractTemplateService.getAllSellerContracts(
                        sellerId,
                        page,
                        pageSize,
                        asc,
                        sortBy));
    }

    @GetMapping
    public List<GetAllContractTemplatesResponse> getAll(
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "25") @Max(200) @Min(1) Integer pageSize,
            @RequestParam(required = false, defaultValue = "false") Boolean asc,
            @RequestParam(required = false, defaultValue = "CREATE_DATE") SortBy sortBy) {

        List<GetAllContractTemplatesResponse> contractTemplateResponseList = ContractTemplateMapper.map(
                contractTemplateService.getAll(
                        onlineUser.getUserId(),
                        page,
                        pageSize,
                        asc,
                        sortBy));
        contractTemplateResponseList.stream()
                .filter(templatesResponse -> templatesResponse.getSide() == Side.BUYING)
                .forEach(templatesResponse -> templatesResponse.setSuggestionState(
                        suggestionService.findLatestForBuyer(templatesResponse.getId(),
                                onlineUser.getUserId())
                                .getSuggestionState()
                ));
        return contractTemplateResponseList
                .stream()
                .filter(templatesResponse ->
                        templatesResponse.getSuggestionState() != SuggestionState.CONFIRMED &&
                                templatesResponse.getSuggestionState() != SuggestionState.SIGNED
                ).collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public GetContractTemplateResponse get(@PathVariable @NotNull Long id) {

        ContractTemplate contractTemplate = contractTemplateService.find(id);

        GetContractTemplateResponse response = ContractTemplateMapper.map(onlineUser.getUserId(), contractTemplate);

        User user = profileService.find(contractTemplate.getSellerId());
        response.setSeller(user);

        return response;
    }

    @GetMapping("/vehicles/metadata")
    public List<MetaDataResponse> getVehicleMetaData() {
        return MetaDataMapper.makeVehicleMetaData();
    }

    @GetMapping("/commodities/metadata")
    public List<MetaDataResponse> getCommodityMetaData() {
        return MetaDataMapper.makeCommodityMetaData();
    }

    @PostMapping("/vehicles")
    @Created
    public CreateContractTemplateResponse create(@Valid @RequestBody CreateVehicleContractTemplateRequest request) {
        VehicleContractTemplate vehicleContractTemplate = modelMapper.map(request, VehicleContractTemplate.class);
        vehicleContractTemplate.setSellerId(onlineUser.getUserId());
        return new CreateContractTemplateResponse(
                contractTemplateService.saveVehicle(vehicleContractTemplate).getId()
        );
    }

    @PutMapping("/vehicles")
    @NoContent
    public void update(@Valid @RequestBody UpdateVehicleContractTemplateRequest request) {
        contractTemplateService.updateVehicle(
                modelMapper.map(request, VehicleContractTemplate.class));
    }

    @PostMapping("/commodities")
    @Created
    public CreateContractTemplateResponse create(@Valid @RequestBody CreateCommodityContractTemplateRequest request) {
        CommodityContractTemplate commodityContractTemplate = modelMapper.map(request, CommodityContractTemplate.class);
        commodityContractTemplate.setSellerId(onlineUser.getUserId());
        return new CreateContractTemplateResponse(
                contractTemplateService.saveCommodity(commodityContractTemplate).getId()
        );
    }

    @PutMapping("/commodities")
    @NoContent
    public void update(@Valid @RequestBody UpdateCommodityContractTemplateRequest request) {
        contractTemplateService.updateCommodity(
                modelMapper.map(request, CommodityContractTemplate.class));
    }

    @PostMapping("/services")
    @Created
    public CreateContractTemplateResponse create(@Valid @RequestBody CreateServiceContractTemplateRequest request) {
        ServiceContractTemplate serviceContractTemplate =
                modelMapper.map(request, ServiceContractTemplate.class);
        serviceContractTemplate.setSellerId(onlineUser.getUserId());
        return new CreateContractTemplateResponse(
                contractTemplateService.saveService(serviceContractTemplate).getId()
        );
    }

    @PutMapping("/services")
    @NoContent
    public void update(@Valid @RequestBody UpdateServiceContractTemplateRequest request) {
        contractTemplateService.updateService(
                modelMapper.map(request, ServiceContractTemplate.class));
    }

    @PutMapping("/{id}/favorite/{favorite}")
    @NoContent
    public void update(@PathVariable @Valid @NotNull Long id,
                       @PathVariable @Valid @NotNull Boolean favorite) {
        contractTemplateService.updateFavoriteStatus(id, onlineUser.getUserId(), favorite);
    }

    @PutMapping("/repeatable-and-viewable")
    @NoContent
    public void update(@Valid @RequestBody UpdateContractTemplateRepeatableAndViewablePropertiesRequest request) {
        contractTemplateService.updateViewableAndRepeatable(request.getTemplateId(),
                request.getViewable(),
                request.getRepeatable(),
                request.getNumberOfRepeats());
    }

}
