package com.vosouq.util;

import com.vosouq.RepeatableTemplateContractTests;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.contract.controller.ContractTemplateController;
import com.vosouq.contract.controller.FileAddressController;
import com.vosouq.contract.controller.SuggestionController;
import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.model.*;
import com.vosouq.contract.repository.SuggestionRepository;
import com.vosouq.contract.service.ContractTemplateService;
import com.vosouq.contract.utills.Address;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.vosouq.contract.utills.Address.*;
import static com.vosouq.util.RandomGeneratorUtility.*;

@Slf4j
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ContractTestUtil {
    private static final Logger logger = LoggerFactory.getLogger(ContractTestUtil.class);

    public static void changeCurrentUser(OnlineUser user, GenericApplicationContext context) {
        log.debug("Switching user to: {}", user);
        if (context.isBeanNameInUse("getOnlineUser")) {
            OnlineUser onlineUser = (OnlineUser) context.getBean("getOnlineUser");
            onlineUser.setClientId(user.getClientId());
            onlineUser.setClientName(user.getClientName());
            onlineUser.setDeviceId(user.getDeviceId());
            onlineUser.setPhoneNumber(user.getPhoneNumber());
            onlineUser.setUserId(user.getUserId());
        } else {
            throw new RuntimeException("'getOnlineUser' bean is not present in the context and cannot be modified.");
        }
    }


    public static CreateVehicleContractTemplateRequest generateVehicleContractTemplate(
            List<FileAddressResponse> files) {
        return generateVehicleContractTemplate(true, false, 1, files);
    }

    public static CreateVehicleContractTemplateRequest generateVehicleContractTemplate(
            boolean isOwner,
            boolean isRepeatable,
            int numberOfRepeats,
            List<FileAddressResponse> files) {
        CreateVehicleContractTemplateRequest randomVehicleContractTemplate = new CreateVehicleContractTemplateRequest(
                generateRandomNumber(1970, 2021),
                generateRandomNumber(0, 400) * 1000,
                randomEnumOf(Gearbox.class),
                randomEnumOf(Fuel.class),
                randomEnumOf(BodyStatus.class),
                randomEnumOf(Color.class),
                isOwner
        );
        return fillGenericDataOf(randomVehicleContractTemplate, isRepeatable, numberOfRepeats, files);
    }

    public static CreateServiceContractTemplateRequest generateServiceContractTemplate(
            List<FileAddressResponse> files) {
        return generateServiceContractTemplate(false, 1, files);
    }

    public static CreateServiceContractTemplateRequest generateServiceContractTemplate(
            boolean isRepeatable,
            int numberOfRepeats,
            List<FileAddressResponse> files) {
        return fillGenericDataOf(new CreateServiceContractTemplateRequest(), isRepeatable, numberOfRepeats, files);
    }


    public static CreateCommodityContractTemplateRequest generateCommodityContractTemplateRequest(
            boolean isRepeatable,
            int numberOfRepeats) {
        CreateCommodityContractTemplateRequest request = new CreateCommodityContractTemplateRequest(
                generateRandomNumber(1, 500) * 1000L,
                generateRandomNumber(1, 100),
                randomEnumOf(Unit.class)
        );
        fillGenericDataOf(request, isRepeatable, numberOfRepeats);
        request.setPrice(request.getPricePerUnit() * request.getQuantity());
        return request;
    }

    public static CreateCommodityContractTemplateRequest generateCommodityContractTemplate(
            List<FileAddressResponse> files) {
        return generateCommodityContractTemplate(false, 1, files);
    }

    public static CreateCommodityContractTemplateRequest generateCommodityContractTemplate(
            boolean isRepeatable,
            int numberOfRepeats,
            List<FileAddressResponse> files) {
        CreateCommodityContractTemplateRequest commodityContractTemplate = new CreateCommodityContractTemplateRequest(
                generateRandomNumber(1, 500) * 1000L,
                generateRandomNumber(1, 100),
                randomEnumOf(Unit.class)
        );
        fillGenericDataOf(commodityContractTemplate, isRepeatable, numberOfRepeats, files);
        commodityContractTemplate.setPrice(
                commodityContractTemplate.getPricePerUnit() * commodityContractTemplate.getQuantity());
        return commodityContractTemplate;
    }

    public static <T extends ContractTemplate> T fillGenericDataOf(T contractTemplate,
                                                                   List<FileAddress> files,
                                                                   Long sellerId) {
        Timestamp timestamp = generateTimestamp();
        contractTemplate.setCity(generateSynthesizedName(7));
        contractTemplate.setCreateDate(timestamp);
        contractTemplate.setDescription(generateRandomString(100));
        contractTemplate.setFiles(files);
        contractTemplate.setFavorite(false);
        contractTemplate.setNumberOfRepeats(1);
        contractTemplate.setProvince(generateSynthesizedName(7));
        contractTemplate.setRepeatable(false);
        contractTemplate.setSellerId(sellerId);
        contractTemplate.setTitle(generateSynthesizedName(7));
        contractTemplate.setUpdateDate(timestamp);
        contractTemplate.setViewable(true);
        return contractTemplate;
    }

    ;

    public static <T extends ConfirmContractTemplateRequest> T fillGenericDataOf(
            T confirmContractTemplateRequest,
            Long contractTemplateId,
            Long suggestionId,
            GetContractTemplateResponse getContractTemplateResponse,
            List<Long> fileIds
    ) {
        confirmContractTemplateRequest.setContractTemplateId(contractTemplateId);
        confirmContractTemplateRequest.setTitle(getContractTemplateResponse.getTitle());
        confirmContractTemplateRequest.setCity(getContractTemplateResponse.getCity());
        confirmContractTemplateRequest.setProvince(getContractTemplateResponse.getProvince());
        confirmContractTemplateRequest.setNeighbourhood(getContractTemplateResponse.getNeighbourhood());
        confirmContractTemplateRequest.setDeliveryDate(System.currentTimeMillis() +
                //Add one to three days to current millis
                RandomGeneratorUtility.generateRandomNumber(1, 4) * 86400000);
        confirmContractTemplateRequest.setDescription(getContractTemplateResponse.getDescription());
        confirmContractTemplateRequest.setFileIds(fileIds);
        confirmContractTemplateRequest.setPaymentTemplates(
                Collections.singletonList(
                        new PaymentTemplateRequest(
                                getContractTemplateResponse.getPrice(),
                                confirmContractTemplateRequest.getDeliveryDate())
                ));
        confirmContractTemplateRequest.setPrice(getContractTemplateResponse.getPrice());
//                        ConfirmContractTemplateRequest.setSignDescription();
        confirmContractTemplateRequest.setSuggestionId(suggestionId);
        confirmContractTemplateRequest.setTerminationPenalty(getContractTemplateResponse.getPrice() * 10 / 100);
        return confirmContractTemplateRequest;
    }

    public static Address getAddress(@RequestParam FileType fileType) {
        Address addressToSave;
        if (fileType.equals(FileType.PRODUCT_IMAGE))
            addressToSave = IMAGES_CASE_FILE;
        else if (fileType.equals(FileType.ATTACHMENT_FILE))
            addressToSave = ATTACHMENTS_CASE_FILE;
        else if (fileType.equals(FileType.OTHER))
            addressToSave = OTHER_CASE_FILE;
        else
            throw new IncompatibleValueException();
        return addressToSave;
    }

    @SuppressWarnings("SameParameterValue")
    public static FileAddressResponse generateImage(FileType fileType, FileAddressController fileAddressController) {
        FileAddressResponse fileAddressResponse = null;
        try {
            fileAddressResponse = fileAddressController.uploadFile(
                    new TestMultipartFile(
                            Objects.requireNonNull(RepeatableTemplateContractTests.class
                                    .getClassLoader()
                                    .getResourceAsStream("images/test.jpg")),
                            RandomGeneratorUtility.generateSynthesizedName(8) + ".jpg"),
                    fileType
            );
            logger.debug("Generated new File with id: {}", fileAddressResponse.getId());
        } catch (IOException e) {
            logger.error("Problem while uploading file.", e);
        }
        return fileAddressResponse;
    }

    public static void createSuggestionForTemplate(GetAllContractTemplatesResponse templatesResponse,
                                                   ContractTemplateService contractTemplateService,
                                                   ContractTemplateController contractTemplateController,
                                                   SuggestionController suggestionController,
                                                   GenericApplicationContext context) {
        ContractTemplate contractTemplate = contractTemplateService.find(templatesResponse.getId());
        logger.debug(contractTemplate.toString());
        CreateSuggestionRequest createSuggestionRequest = new CreateSuggestionRequest();
        createSuggestionRequest.setContractTemplateId(templatesResponse.getId());
        createSuggestionRequest.setPrice(templatesResponse.getTotalPrice());
        if (logger.isDebugEnabled()) {
            GetContractTemplateResponse contractTemplateResponse =
                    contractTemplateController.get(templatesResponse.getId());

            logger.debug("Creating suggestion request for template: {}.\nBuyer: {}\nSeller: {}",
                    templatesResponse,
                    context.getBean("getOnlineUser").toString(),
                    contractTemplateResponse.getSeller());
        }
        suggestionController.create(createSuggestionRequest);
    }

    public static void createSuggestionForTemplates(List<GetAllContractTemplatesResponse> allTemplatesOfSeller,
                                                    ContractTemplateService contractTemplateService,
                                                    ContractTemplateController contractTemplateController,
                                                    SuggestionController suggestionController,
                                                    GenericApplicationContext context) {
        allTemplatesOfSeller.forEach(templatesResponse ->
                createSuggestionForTemplate(templatesResponse,
                        contractTemplateService,
                        contractTemplateController,
                        suggestionController,
                        context));
    }

    private static <T extends CreateContractTemplateRequest> T fillGenericDataOf(T contractTemplate,
                                                                                 boolean isRepeatable,
                                                                                 int numberOfRepeats,
                                                                                 List<FileAddressResponse> files) {
        contractTemplate.setNumberOfRepeats(numberOfRepeats);
        contractTemplate.setRepeatable(isRepeatable);
        contractTemplate.setPrice(generateRandomNumber(10, 2000) * 1_000_000L);
//        contractTemplate.setPrice(20_000_000L);
        contractTemplate.setViewable(true);
        contractTemplate.setTitle(generateSynthesizedName(generateRandomNumber(5, 8)));
        contractTemplate.setFileIds(files.stream().map(FileAddressResponse::getId).collect(Collectors.toList()));
        contractTemplate.setCity(generateSynthesizedName(3));
        contractTemplate.setProvince(generateSynthesizedName(5));
        return contractTemplate;
    }

    private static <T extends CreateContractTemplateRequest> T fillGenericDataOf(T contractTemplateRequest,
                                                                                 boolean isRepeatable,
                                                                                 int numberOfRepeats) {
        contractTemplateRequest.setNumberOfRepeats(numberOfRepeats);
        contractTemplateRequest.setRepeatable(isRepeatable);
        contractTemplateRequest.setPrice(generateRandomNumber(50, 1200) * 1000000L);
        contractTemplateRequest.setViewable(true);
        contractTemplateRequest.setTitle(generateSynthesizedName(generateRandomNumber(5, 8)));
        return contractTemplateRequest;
    }

}
