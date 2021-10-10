package com.vosouq;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.config.ContractKafkaConfig;
import com.vosouq.config.ContractWireMockConfig;
import com.vosouq.contract.ContractApplication;
import com.vosouq.contract.controller.ContractTemplateController;
import com.vosouq.contract.controller.CurrentContractController;
import com.vosouq.contract.controller.FileAddressController;
import com.vosouq.contract.controller.SuggestionController;
import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.model.FileType;
import com.vosouq.contract.model.SuggestionState;
import com.vosouq.contract.service.ContractTemplateService;
import com.vosouq.contract.utills.SortBy;
import com.vosouq.mock.BookkeepingMocks;
import com.vosouq.mock.MessageMocks;
import com.vosouq.mock.ProfilesMocks;
import com.vosouq.mock.ScoringCommunicatorMocks;
import com.vosouq.util.ContractTestUtil;
import com.vosouq.util.RandomGeneratorUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.vosouq.util.ContractTestUtil.*;

@SpringBootTest(classes = ContractApplication.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ContractWireMockConfig.class, ContractKafkaConfig.class})
public class ScoringTests {
    private static final Logger logger = LoggerFactory.getLogger(RepeatableTemplateContractTests.class);
    private static final long SELLER_ID = 100L;

    private static final List<OnlineUser> buyers = new ArrayList<>();
    private static final List<OnlineUser> sellers = new ArrayList<>();

    @Autowired
    private GenericApplicationContext context;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private WireMockServer mockServer;

    @Autowired
    private ContractTemplateService contractTemplateService;

    @Autowired
    private ContractTemplateController contractTemplateController;

    @Autowired
    private SuggestionController suggestionController;

    @Autowired
    private CurrentContractController currentContractController;


    @Autowired
    private FileAddressController fileAddressController;

    @BeforeEach
    void setupBeforeEach() {
        BookkeepingMocks.setupMockBookkeepingResponse(mockServer);
        ProfilesMocks.setupMockProfileResponse(mockServer);
        MessageMocks.setupMockMessageResponse(mockServer);
        ScoringCommunicatorMocks.setupMockScoringCommunicatorResponse(mockServer);
    }

    @BeforeAll
    public static void initialize() {
        /*
         * Create Users
         */
        logger.info("Creating dummy users...");
        sellers.add(new OnlineUser(
                SELLER_ID,
                SELLER_ID,
                "09120000000",
                "test-seller-client-id",
                "test-seller-client-name"));
        for (int i = 0; i < 5; i++) {
            buyers.add(new OnlineUser(i, i,
                    "09120000000",
                    "test-buyer" + i + "-client-id",
                    "test-buyer" + i + "-client-name"));
        }
        logger.info("Finished creating dummy users.");
    }

    @Test
    @DirtiesContext
    @DisplayName("Creates single contract template, makes suggestion by one user, accepts suggestion, " +
            "signs them by both parties, does the payment and then delivers contract.")
    void sendSingleContractToScoring() {
        /*
         * Create Seller contract templates for Vehicle, Service and Commodity types
         */
        logger.info("STAGE-1: Creating seller contract templates...");
        changeCurrentUser(sellers.get(0), context);
        contractTemplateController.create(generateVehicleContractTemplate(
                true,
                false,
                1,
                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))));

        logger.info("Finished creating seller contract templates.");

        logger.info("STAGE-2-1: Creating buyer{} suggestion...", 0);
        changeCurrentUser(buyers.get(0), context);
        List<GetAllContractTemplatesResponse> allTemplatesOfSeller =
                getAllTemplatesOfSeller();
        logger.debug("Number of available seller contracts: {}", allTemplatesOfSeller.size());
        new TransactionTemplate(this.transactionManager).executeWithoutResult(status ->
                createSuggestionForTemplates(allTemplatesOfSeller,
                        contractTemplateService,
                        contractTemplateController,
                        suggestionController,
                        context));
        logger.info("Finished creating buyer{} suggestions.", 0);

        /*
         * Confirm all the suggestions for all created templates
         */
        logger.info("STAGE-2-2: Confirming all available suggestions by seller...");
        changeCurrentUser(sellers.get(0), context);
        new TransactionTemplate(this.transactionManager).executeWithoutResult(status ->
                confirmContractSuggestions(allTemplatesOfSeller));
        logger.info("Finished confirming all available suggestions.");

        /*
         * Sign contract by buyer
         */
        logger.info("STAGE-2-3: Signing contract by buyer.");
        changeCurrentUser(buyers.get(0), context);
        currentContractController.getAll(1, 250).forEach(contractsResponse ->
                currentContractController.signContractByBuyer(contractsResponse.getId())
        );
        logger.info("Finished signing contracts");


        logger.info("STAGE-3: making payments for all signed contracts");
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.BUYING)
                    .forEach(contract ->
                            currentContractController.payContractPayment(contract.getId(), contract.getPrice()));
        });

        logger.info("Finished payments");

        logger.info("STAGE-4: Delivering contracts");
        sellers.forEach(seller -> {
            changeCurrentUser(seller, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.SELLING)
                    .forEach(contractResponse ->
                            currentContractController.deliverContractSubjectBySeller(contractResponse.getId()));
        });
        logger.info("Finished delivering");

        logger.info("STAGE-5: Adding Seal of approval by buyer");
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.BUYING)
                    .forEach(contractsResponse ->
                            currentContractController.sealOfApprovalByBuyer(contractsResponse.getId(), true));
        });
        logger.info("Finished seal of approval stage");

    }

    @Test
    @DirtiesContext
    @DisplayName("Creates multiple one step payment contracts by one buyer makes suggestions by multiple users" +
            " signs them by both parties and then" +
            " delivers the contract in a sunny day scenario")
    void sendMultipleContractToScoring() {
        /*
         * Create Seller contract templates for Vehicle, Service and Commodity types
         */
        logger.info("STAGE-1: Creating seller contract templates...");
        changeCurrentUser(sellers.get(0), context);
        contractTemplateController.create(generateVehicleContractTemplate(
                true,
                true,
                3,
                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
        ));
        contractTemplateController.create(generateCommodityContractTemplate(
                true,
                2,
                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
        ));
        contractTemplateController.create(generateServiceContractTemplate(
                true,
                2,
                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
        ));

        logger.info("Finished creating seller contract templates.");

        //noinspection ConstantConditions
        for (int i = 0; i < 4; i++) {
            logger.info("STAGE-2-{}-1: Creating buyer{} suggestions...", i, i);
            changeCurrentUser(buyers.get(i), context);
            List<GetAllContractTemplatesResponse> allTemplatesOfSeller =
                    getAllTemplatesOfSeller();
            logger.debug("Number of available seller contracts: {}", allTemplatesOfSeller.size());
            new TransactionTemplate(this.transactionManager).executeWithoutResult(status ->
                    createSuggestionForTemplates(allTemplatesOfSeller,
                            contractTemplateService,
                            contractTemplateController,
                            suggestionController,
                            context));
            logger.info("Finished creating buyer{} suggestions.", i);

            /*
             * Confirm all the suggestions for all created templates
             */
            logger.info("STAGE-2-{}-2: Confirming all available suggestions by seller...", i);
            changeCurrentUser(sellers.get(0), context);
            new TransactionTemplate(this.transactionManager).executeWithoutResult(status ->
                    confirmContractSuggestions(allTemplatesOfSeller));
            logger.info("Finished confirming all available suggestions.");

            /*
             * Sign contract by buyer
             */
            logger.info("STAGE-2-{}-3: Signing contract by buyer.", i);
            changeCurrentUser(buyers.get(i), context);
            currentContractController.getAll(1, 250).forEach(contractsResponse ->
                    currentContractController.signContractByBuyer(contractsResponse.getId())
            );
            logger.info("Finished signing contracts");

        }

        logger.info("STAGE-3: making payments for all signed contracts");
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.BUYING)
                    .forEach(contract ->
                            currentContractController.payContractPayment(contract.getId(), contract.getPrice()));
        });

        logger.info("Finished payments");

        logger.info("STAGE-4: Delivering contracts");
        sellers.forEach(seller -> {
            changeCurrentUser(seller, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.SELLING)
                    .forEach(contractResponse ->
                            currentContractController.deliverContractSubjectBySeller(contractResponse.getId()));
        });
        logger.info("Finished delivering");

        logger.info("STAGE-5: Adding Seal of approval by buyer");
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.BUYING)
                    .forEach(contractsResponse ->
                            currentContractController.sealOfApprovalByBuyer(contractsResponse.getId(), true));
        });
        logger.info("Finished seal of approval stage");

    }

    /*
     * Finds all unattended suggestions and confirms them.
     */
    private void confirmContractSuggestions(List<GetAllContractTemplatesResponse> sellerTemplatesList) {

        sellerTemplatesList.forEach(templatesResponse -> {
            List<SuggestionForSellerResponse> suggestionList =
                    suggestionController.readAllForSeller(
                            templatesResponse.getId(),
                            1,
                            25,
                            false,
                            SortBy.UPDATE_DATE);
            suggestionList.stream().filter(suggestionResponse ->
                    suggestionResponse.getSuggestionState() == SuggestionState.UNATTENDED
            ).forEach(suggestionResponse -> {
                GetContractTemplateResponse contractTemplateResponse =
                        contractTemplateController.get(templatesResponse.getId());
                switch (contractTemplateResponse.getContractType()) {
                    case SERVICE:
                        ConfirmServiceContractTemplateRequest serviceRequest =
                                new ConfirmServiceContractTemplateRequest();
                        ContractTestUtil.fillGenericDataOf(serviceRequest,
                                templatesResponse.getId(),
                                suggestionResponse.getId(),
                                contractTemplateResponse,
                                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE,
                                        fileAddressController).getId())
                        );
                        currentContractController.confirmServiceSuggestion(serviceRequest);
                        break;
                    case VEHICLE:
                        VehicleContractTemplateResponse vehicleContractTemplateResponse =
                                (VehicleContractTemplateResponse) contractTemplateResponse;
                        ConfirmVehicleContractTemplateRequest vehicleRequest =
                                new ConfirmVehicleContractTemplateRequest();
                        ContractTestUtil.fillGenericDataOf(vehicleRequest,
                                templatesResponse.getId(),
                                suggestionResponse.getId(),
                                vehicleContractTemplateResponse,
                                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE,
                                        fileAddressController).getId())
                        );

                        ConfirmVehicleAttachmentRequest vehicleAttachment;
                        vehicleAttachment = new ConfirmVehicleAttachmentRequest(
                                RandomGeneratorUtility.generateNumberString(10),
                                RandomGeneratorUtility.generateNumberString(20),
                                RandomGeneratorUtility.generateNumberString(20),
                                generateImage(FileType.ATTACHMENT_FILE, fileAddressController).getId(),
                                generateImage(FileType.ATTACHMENT_FILE, fileAddressController).getId(),
                                generateImage(FileType.ATTACHMENT_FILE, fileAddressController).getId()
                        );

                        vehicleRequest.setBodyStatus(vehicleContractTemplateResponse.getBodyStatus());
                        vehicleRequest.setColor(vehicleContractTemplateResponse.getColor());
                        vehicleRequest.setFuel(vehicleContractTemplateResponse.getFuel());
                        vehicleRequest.setGearbox(vehicleContractTemplateResponse.getGearbox());
                        vehicleRequest.setIsOwner(vehicleContractTemplateResponse.getIsOwner());
                        vehicleRequest.setManufactureYear(vehicleContractTemplateResponse.getManufactureYear());
                        vehicleRequest.setUsage(vehicleContractTemplateResponse.getUsage());
                        vehicleRequest.setVehicleAttachment(vehicleAttachment);
                        currentContractController.confirmVehicleSuggestion(vehicleRequest);
                        break;
                    case COMMODITY:
                        CommodityContractTemplateResponse commodityContractTemplateResponse =
                                (CommodityContractTemplateResponse) contractTemplateResponse;
                        ConfirmCommodityContractTemplateRequest commodityRequest =
                                new ConfirmCommodityContractTemplateRequest();
                        ContractTestUtil.fillGenericDataOf(commodityRequest,
                                templatesResponse.getId(),
                                suggestionResponse.getId(),
                                commodityContractTemplateResponse,
                                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE,
                                        fileAddressController).getId())
                        );
                        commodityRequest.setPricePerUnit(commodityContractTemplateResponse.getPricePerUnit());
                        commodityRequest.setQuantity(commodityContractTemplateResponse.getQuantity());
                        commodityRequest.setUnit(commodityContractTemplateResponse.getUnit());
                        currentContractController.confirmCommoditySuggestion(commodityRequest);
                        break;
                }
            });
        });
    }

    private List<GetAllContractTemplatesResponse> getAllTemplatesOfSeller() {
        return contractTemplateController.getAllTemplatesOfSeller(
                SELLER_ID,
                1,
                25,
                false,
                SortBy.UPDATE_DATE
        );
    }
}
