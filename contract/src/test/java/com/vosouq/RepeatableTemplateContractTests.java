package com.vosouq;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.config.ContractKafkaConfig;
import com.vosouq.config.ContractWireMockConfig;
import com.vosouq.contract.ContractApplication;
import com.vosouq.contract.controller.*;
import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.model.ContractState;
import com.vosouq.contract.model.ContractTemplate;
import com.vosouq.contract.model.FileType;
import com.vosouq.contract.model.SuggestionState;
import com.vosouq.contract.repository.ContractRepository;
import com.vosouq.contract.repository.ContractTemplateRepository;
import com.vosouq.contract.service.ContractTemplateService;
import com.vosouq.contract.utills.SortBy;
import com.vosouq.mock.BookkeepingMocks;
import com.vosouq.mock.MessageMocks;
import com.vosouq.mock.ProfilesMocks;
import com.vosouq.mock.ScoringCommunicatorMocks;
import com.vosouq.util.ContractTestUtil;
import com.vosouq.util.RandomGeneratorUtility;
import org.junit.jupiter.api.*;
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

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.vosouq.util.ContractTestUtil.*;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ContractApplication.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ContractWireMockConfig.class, ContractKafkaConfig.class})
public class RepeatableTemplateContractTests {
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
    private ContractTemplateRepository contractTemplateRepository;

    @Autowired
    private SuggestionController suggestionController;

    @Autowired
    private CurrentContractController currentContractController;

    @Autowired
    private ContractHistoryController contractHistoryController;

    @Autowired
    private FileAddressController fileAddressController;

    @Autowired
    private ContractRepository contractRepository;

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
    @DisplayName("Create, make a suggestion, confirm a suggestion and then wait for the contract to expire and verify" +
            " that the stale contracts are recovered to available repeatable templates.")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void recoverStaleContracts() {

        logger.info("STAGE-1: Creating seller contract template...");
        changeCurrentUser(sellers.get(0), context);
        final long vehicleTemplateId = contractTemplateController.create(generateVehicleContractTemplate(
                true,
                true,
                2,
                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
        )).getId();

        logger.info("Finished creating seller contract template.");

        /*
         * Creating Buyer suggestion
         */
        logger.info("STAGE-2: Creating buyer suggestion...");
        ContractTemplate vehicleTemplate = contractTemplateRepository.findById(vehicleTemplateId).orElseThrow();
        changeCurrentUser(buyers.get(0), context);
        logger.debug("Number of remaining repeats: {}", vehicleTemplate.getNumberOfRepeats());
        assertEquals(2, vehicleTemplate.getNumberOfRepeats().intValue(), "Number of repeats must be 2");
        new TransactionTemplate(this.transactionManager)
                .executeWithoutResult(status -> createSuggestionForTemplates(getAllTemplatesOfSeller(),
                        contractTemplateService,
                        contractTemplateController,
                        suggestionController,
                        context));
        logger.info("Finished creating buyer suggestions.");

        /*
         * Confirm all the suggestions for all created templates
         */
        logger.info("STAGE-3: Confirming all available suggestions by seller...");
        changeCurrentUser(sellers.get(0), context);
        new TransactionTemplate(this.transactionManager)
                .executeWithoutResult(status -> confirmContractSuggestions(getAllTemplatesOfSeller()));
        vehicleTemplate = contractTemplateRepository.findById(vehicleTemplateId).orElseThrow();
        assertEquals(1,
                vehicleTemplate.getNumberOfRepeats().intValue(),
                "One repeat must be available after confirmation.");
        logger.info("Finished confirming all available suggestions.");

        /*
         * Wait for clean-up service to finish its work
         */
        logger.info("STAGE-4: Waiting for the clean-up task to revoke current contract and revert number of repeats.");
        await().pollInSameThread().atMost(Duration.ofSeconds(10)).until(() -> {
            @NotNull Integer numberOfRepeats =
                    contractTemplateRepository.findById(vehicleTemplateId).orElseThrow().getNumberOfRepeats();
            logger.debug("Number of repeats: {}", numberOfRepeats);
            return numberOfRepeats == 2;
        });
        logger.info("Finished waiting for the clean-up task");

        /*
         * Sign contract by buyer after it is stale and expect to get exception
         */
        logger.info("Signing contract by buyer.");
        changeCurrentUser(buyers.get(0), context);
        assertThrows(IncompatibleValueException.class, () ->
                contractRepository.findByBuyerId(buyers.get(0).getUserId()).forEach(contractsResponse ->
                        currentContractController.signContractByBuyer(contractsResponse.getId())
                ));

        logger.info("Finished signing contracts");

        /*
         * Creating another Buyer suggestion
         */
        logger.info("STAGE-5: Creating buyer suggestion...");
        vehicleTemplate = contractTemplateRepository.findById(vehicleTemplateId).orElseThrow();
        changeCurrentUser(buyers.get(0), context);
        logger.debug("Number of remaining repeats: {}", vehicleTemplate.getNumberOfRepeats());
        assertEquals(2, vehicleTemplate.getNumberOfRepeats().intValue(), "Number of repeats must be 2");
        new TransactionTemplate(this.transactionManager)
                .executeWithoutResult(status -> createSuggestionForTemplates(getAllTemplatesOfSeller(),
                        contractTemplateService,
                        contractTemplateController,
                        suggestionController,
                        context));
        logger.info("Finished creating buyer suggestions.");

        /*
         * Confirm all the suggestions for all created templates by seller
         */
        logger.info("STAGE-6: Confirming all available suggestions by seller...");
        changeCurrentUser(sellers.get(0), context);
        new TransactionTemplate(this.transactionManager)
                .executeWithoutResult(status -> confirmContractSuggestions(getAllTemplatesOfSeller()));
        vehicleTemplate = contractTemplateRepository.findById(vehicleTemplateId).orElseThrow();
        assertEquals(1,
                vehicleTemplate.getNumberOfRepeats().intValue(),
                "One repeat must be available after confirmation.");
        logger.info("Finished confirming all available suggestions.");

        /*
         * Sign contract by buyer
         */
        logger.info("STAGE-7: Signing all confirmed suggestions as buyer 0.");
        changeCurrentUser(buyers.get(0), context);
        assertTrue(currentContractController.getAll(1, 250).stream().anyMatch(contractsResponse ->
                contractsResponse.getState() == ContractState.WAIT_FOR_SIGNING));
        currentContractController.getAll(1, 250).stream().filter(contractsResponse ->
                contractsResponse.getState() == ContractState.WAIT_FOR_SIGNING).forEach(contractsResponse ->
                currentContractController.signContractByBuyer(contractsResponse.getId()));
        logger.info("Finished signing contracts.");

        logger.info("STAGE-8: getting history for stale contracts with buyer 0.");
        List<GetAllContractsResponse> contractsHistory
                = contractHistoryController.getHistory(null, 1, 200);
        contractsHistory.forEach(history -> logger.debug(history.toString()));
        assertTrue(contractsHistory.stream().anyMatch(history -> history.getState().equals(ContractState.STALE)),
                "Expecting at least one stale contract in history for buyer 0");
        logger.info("Finished verifying history");
//        /*
//         * Make sure signed contract will not be removed
//         */
//        logger.info("STAGE-8: Waiting to see clean-up service skips signed suggestion");
//        logger.info("Finished waiting");
    }

    @Test
    @DisplayName("create, make suggestion, confirm suggestion, sign by buyer and check if repeatable templates are " +
            "handled correctly for all template types.")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void createAndSign() {

        /*
         * Create Seller contract templates for Vehicle, Service and Commodity types
         */
        logger.info("STAGE-1: Creating seller contract templates...");
        changeCurrentUser(sellers.get(0), context);
        assertEquals(0, getAllTemplatesOfSeller().size(), "Database must be empty.");
        ContractTemplate vehicleTemplate = contractTemplateService.find(
                contractTemplateController.create(generateVehicleContractTemplate(
                        true,
                        true,
                        3,
                        Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
                )).getId());
        ContractTemplate commodityTemplate = contractTemplateService.find(
                contractTemplateController.create(generateCommodityContractTemplate(
                        true,
                        2,
                        Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
                )).getId());
        ContractTemplate serviceTemplate = contractTemplateService.find(
                contractTemplateController.create(generateServiceContractTemplate(
                        true,
                        2,
                        Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
                )).getId());


        logger.info("Reading created contracts with getAll to verify that they are registered");
        List<Long> templateIds = contractTemplateController.getAll(1, 200, false, SortBy.NONE)
                .stream().map(GetAllContractTemplatesResponse::getId).collect(Collectors.toList());
        if (logger.isDebugEnabled()) {
            logger.debug("List of found template ids:");
            templateIds.forEach(id -> logger.debug(id.toString()));
        }
        assertTrue(List.of(vehicleTemplate, commodityTemplate, serviceTemplate).stream().allMatch(template ->
                templateIds.contains(template.getId())
        ));
        logger.info("Finished creating seller contract templates.");

        List<String> templateTitles = List.of(serviceTemplate.getTitle(),
                commodityTemplate.getTitle(),
                vehicleTemplate.getTitle());
        //noinspection ConstantConditions
        for (int i = 0; i < 4; i++) {
            logger.info("STAGE-2-{}-1: Creating buyer{} suggestions...", i, i);
            changeCurrentUser(buyers.get(i), context);
            List<GetAllContractTemplatesResponse> allTemplatesOfSeller =
                    getAllTemplatesOfSeller();
            logger.debug("Number of available seller contracts: {}", allTemplatesOfSeller.size());
            logger.debug("Verifying available contracts...");
            switch (i) {
                //noinspection ConstantConditions
                case 0:
                case 1:
                    assertEquals(3, allTemplatesOfSeller.size());
                    assertTrue(allTemplatesOfSeller.stream().allMatch(template ->
                            templateTitles.contains(template.getTitle())
                    ));
                    break;
                case 2:
                    assertEquals(1, allTemplatesOfSeller.size());
                    assertTrue(allTemplatesOfSeller.stream().allMatch(template ->
                            template.getTitle().equals(vehicleTemplate.getTitle())));
                    break;
                case 3:
                    assertEquals(0, allTemplatesOfSeller.size());
            }
            logger.debug("Finished verifying available contracts.");
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

        /*
         * Verify number of available contracts by another user
         */
        logger.info("STAGE-3: Verifying number of contracts...");
        changeCurrentUser(buyers.get(4), context);
        List<GetAllContractTemplatesResponse> allTemplatesOfSeller =
                contractTemplateController.getAll(1, 200, false, SortBy.NONE);
        allTemplatesOfSeller.forEach(
                templatesResponse ->
                        logger.debug(templatesResponse.toString())
        );
        assertTrue(allTemplatesOfSeller.isEmpty(), "Templates for buyer must be empty.");
        changeCurrentUser(sellers.get(0), context);
        allTemplatesOfSeller =
                contractTemplateController.getAll(1, 200, false, SortBy.NONE);
        allTemplatesOfSeller.forEach(
                templatesResponse ->
                        logger.debug(templatesResponse.toString())
        );
        assertTrue(allTemplatesOfSeller.isEmpty(), "Templates for seller must be empty.");
        logger.info("Finished verifying available contracts.");

    }

    @Test
    @DisplayName("Create a contract template and edit viewable and repeatable through dedicated endpoint.")
    @DirtiesContext
    void createAndEditViewableAndRepeatable() {
        /*
         * Create Seller contract templates for Vehicle and Service types
         */
        logger.info("STAGE-1: Creating seller contract templates...");
        changeCurrentUser(sellers.get(0), context);
        assertEquals(0, getAllTemplatesOfSeller().size(), "Database must be empty.");
        ContractTemplate vehicleTemplate = contractTemplateService.find(
                contractTemplateController.create(generateVehicleContractTemplate(
                        true,
                        true,
                        3,
                        Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
                )).getId());

        ContractTemplate serviceTemplate = contractTemplateService.find(
                contractTemplateController.create(generateServiceContractTemplate(
                        true,
                        2,
                        Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
                )).getId());

        logger.info("Finished creating seller contract templates.");

        logger.info("STAGE-2: Updating viewable and repeatable");
        contractTemplateController.update(new UpdateContractTemplateRepeatableAndViewablePropertiesRequest(
                vehicleTemplate.getId(),
                false,
                1,
                false
        ));
        ContractTemplate contractTemplate = contractTemplateService.find(vehicleTemplate.getId());
        assertEquals(1,
                contractTemplate.getNumberOfRepeats().intValue(),
                "Number of repeats ");
        assertFalse(contractTemplate.getRepeatable());
        assertFalse(contractTemplate.getViewable());

        assertThrows(IncompatibleValueException.class, () ->
                contractTemplateController.update(new UpdateContractTemplateRepeatableAndViewablePropertiesRequest(
                        serviceTemplate.getId(),
                        true,
                        0,
                        false
                )));

        contractTemplateController.update(new UpdateContractTemplateRepeatableAndViewablePropertiesRequest(
                vehicleTemplate.getId(),
                true,
                2,
                false
        ));
        contractTemplate = contractTemplateService.find(vehicleTemplate.getId());
        assertEquals(2, contractTemplate.getNumberOfRepeats());
        assertTrue(contractTemplate.getRepeatable());
        assertFalse(contractTemplate.getViewable());

        contractTemplateController.update(new UpdateContractTemplateRepeatableAndViewablePropertiesRequest(
                vehicleTemplate.getId(),
                false,
                2,
                true
        ));

        contractTemplate = contractTemplateService.find(vehicleTemplate.getId());
        assertEquals(1, contractTemplate.getNumberOfRepeats());
        assertFalse(contractTemplate.getRepeatable());
        assertTrue(contractTemplate.getViewable());

        logger.info("Finished updating viewable and repeatable");
    }

    @Test
    @DirtiesContext
    @DisplayName("Get History of contract.")
    void getHistoryTest() {
        /*
         * Create Seller contract templates for Vehicle, Service and Commodity types
         */
        logger.info("STAGE-1: Creating seller contract templates...");
        changeCurrentUser(sellers.get(0), context);
        assertEquals(0, getAllTemplatesOfSeller().size(), "Database must be empty.");
        ContractTemplate vehicleTemplate = contractTemplateService.find(
                contractTemplateController.create(generateVehicleContractTemplate(
                        true,
                        true,
                        3,
                        Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
                )).getId());
        ContractTemplate commodityTemplate = contractTemplateService.find(
                contractTemplateController.create(generateCommodityContractTemplate(
                        true,
                        2,
                        Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
                )).getId());
        ContractTemplate serviceTemplate = contractTemplateService.find(
                contractTemplateController.create(generateServiceContractTemplate(
                        true,
                        2,
                        Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
                )).getId());

        logger.info("Finished creating seller contract templates.");

        List<String> templateTitles = List.of(serviceTemplate.getTitle(),
                commodityTemplate.getTitle(),
                vehicleTemplate.getTitle());
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

        /*
         * Verify number of available contracts by another user
         */
        logger.info("STAGE-3: Verifying number of contracts...");
        changeCurrentUser(buyers.get(4), context);
        List<GetAllContractTemplatesResponse> allTemplatesOfSeller =
                contractTemplateController.getAll(1, 200, false, SortBy.NONE);
        allTemplatesOfSeller.forEach(
                templatesResponse ->
                        logger.debug(templatesResponse.toString())
        );
        changeCurrentUser(sellers.get(0), context);
        allTemplatesOfSeller =
                contractTemplateController.getAll(1, 200, false, SortBy.NONE);
        allTemplatesOfSeller.forEach(
                templatesResponse ->
                        logger.debug(templatesResponse.toString())
        );
        logger.info("Finished verifying available contracts.");

        /*
         * Checking history for done contracts
         */
        logger.info("STAGE-4: Verifying contents of history");
        List<GetAllContractsResponse> contractsHistory =
                contractHistoryController.getHistory("", 1, 200);
        if (logger.isDebugEnabled()) {
            contractsHistory.forEach(history -> logger.debug(history.toString()));
        }
        assertTrue(contractsHistory.isEmpty(), "History must be empty");
        logger.info("Finished verifying history");

        logger.info("STAGE-5: making payments for all signed contracts");
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.BUYING)
                    .forEach(contract ->
                            currentContractController.payContractPayment(contract.getId(), contract.getPrice()));
        });

        logger.info("Finished payments");

        logger.info("STAGE-6: Delivering contracts");
        sellers.forEach(seller -> {
            changeCurrentUser(seller, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.SELLING)
                    .forEach(contractResponse ->
                            currentContractController.deliverContractSubjectBySeller(contractResponse.getId()));
        });
        logger.info("Finished delivering");

        logger.info("STAGE-7: Adding Seal of approval by buyer");
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            currentContractController.getAll(1, 200)
                    .stream().filter(contractsResponse -> contractsResponse.getSide() == Side.BUYING)
                    .forEach(contractsResponse ->
                            currentContractController.sealOfApprovalByBuyer(contractsResponse.getId(), true));
        });
        logger.info("Finished seal of approval stage");

        logger.info("STAGE-8: Verifying contents of history");
        logger.debug("getting all available contract history...");
        changeCurrentUser(sellers.get(0), context);
        contractsHistory =
                contractHistoryController.getHistory("", 1, 200);
        if (logger.isDebugEnabled()) {
            contractsHistory.forEach(history -> logger.debug(history.toString()));
        }
        assertFalse(contractsHistory.isEmpty(), "History must NOT be empty");

        logger.debug("Testing history lookup with terms...");
        // Creating a random search term based on vehicle contract
        int start = RandomGeneratorUtility.generateRandomNumber(0, vehicleTemplate.getTitle().length() - 3);
        int end = RandomGeneratorUtility.generateRandomNumber(start + 1, vehicleTemplate.getTitle().length());
        String searchTerm = vehicleTemplate.getTitle().substring(start, end);
        logger.info("Looking up search term: {}", searchTerm);
        contractsHistory = contractHistoryController.getHistory(
                searchTerm,
                1,
                200);
        if (logger.isDebugEnabled()) {
            contractsHistory.forEach(contractsResponse -> logger.debug(contractsResponse.toString()));
        }
        assertFalse(contractsHistory.isEmpty(), "Search result must not be empty");
        assertTrue(contractsHistory.size() >=
                        vehicleTemplate.getNumberOfRepeats(),
                "expecting at least the same number of results as there are available vehicle contracts");

        assertTrue(contractsHistory.stream().allMatch(contractResponse -> {
                    logger.debug("Contract history title: {} \nSearch Term: {}",
                            contractResponse.getTitle(),
                            searchTerm);
                    return contractResponse.getTitle().contains(searchTerm);
                }
        ));
        logger.info("Finished verifying history.");
    }

    @Test
    @DirtiesContext
    @DisplayName("Creates templates, make suggestions on them and checks the suggestion state is loaded in getAll response")
    void getAllContractTemplatesSuggestionStatusTest() {
        logger.info("STAGE-1: Creating seller contract template...");
        changeCurrentUser(sellers.get(0), context);
        final long vehicleTemplateId = contractTemplateController.create(generateVehicleContractTemplate(
                true,
                true,
                2,
                Collections.singletonList(generateImage(FileType.PRODUCT_IMAGE, fileAddressController))
        )).getId();

        logger.info("Finished creating seller contract template.");

        /*
         * Creating Buyer suggestion
         */
        logger.info("STAGE-2: Creating buyer suggestion...");
        ContractTemplate vehicleTemplate = contractTemplateRepository.findById(vehicleTemplateId).orElseThrow();
        changeCurrentUser(buyers.get(0), context);
        logger.debug("Number of remaining repeats: {}", vehicleTemplate.getNumberOfRepeats());
        assertEquals(2, vehicleTemplate.getNumberOfRepeats().intValue(), "Number of repeats must be 2");
        new TransactionTemplate(this.transactionManager)
                .executeWithoutResult(status -> createSuggestionForTemplates(getAllTemplatesOfSeller(),
                        contractTemplateService,
                        contractTemplateController,
                        suggestionController,
                        context));
        logger.info("Finished creating buyer suggestions.");

        logger.info("STAGE-3: Checking that suggestion state is loaded");
        contractTemplateController.getAll(1, 25, false, SortBy.NONE).stream()
                .filter(templateResponse -> templateResponse.getSide() == Side.BUYING)
                .forEach(templateResponse -> {
                            assertTrue(List.of(SuggestionState.values()).stream().anyMatch(suggestionState ->
                                            suggestionState == templateResponse.getSuggestionState()),
                                    "Buying template must have a valid suggestion state.");
                            logger.debug("Template Suggestion State: {}", templateResponse.getSuggestionState());
                        }
                );
        logger.info("Finished checking suggestion state");

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
