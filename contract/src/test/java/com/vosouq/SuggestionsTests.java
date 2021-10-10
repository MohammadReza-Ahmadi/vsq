package com.vosouq;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.config.ContractKafkaConfig;
import com.vosouq.config.ContractWireMockConfig;
import com.vosouq.contract.ContractApplication;
import com.vosouq.contract.config.SchedulingSwitch;
import com.vosouq.contract.controller.ContractTemplateController;
import com.vosouq.contract.controller.FileAddressController;
import com.vosouq.contract.controller.SuggestionController;
import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.exception.SuggestionNotFoundException;
import com.vosouq.contract.model.ContractTemplate;
import com.vosouq.contract.model.FileType;
import com.vosouq.contract.model.Suggestion;
import com.vosouq.contract.model.SuggestionState;
import com.vosouq.contract.repository.ContractTemplateRepository;
import com.vosouq.contract.repository.SuggestionRepository;
import com.vosouq.contract.service.ContractTemplateService;
import com.vosouq.contract.utills.SortBy;
import com.vosouq.mock.BookkeepingMocks;
import com.vosouq.mock.MessageMocks;
import com.vosouq.mock.ProfilesMocks;
import com.vosouq.mock.ScoringCommunicatorMocks;
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

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.vosouq.util.ContractTestUtil.*;
import static com.vosouq.util.RandomGeneratorUtility.generateRandomNumber;
import static com.vosouq.util.RandomGeneratorUtility.randomEnumOf;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ContractApplication.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ContractWireMockConfig.class, ContractKafkaConfig.class})
public class SuggestionsTests {
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
    private SchedulingSwitch schedulingSwitch;

    @Autowired
    private SuggestionController suggestionController;

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private ContractTemplateController contractTemplateController;

    @Autowired
    private ContractTemplateService contractTemplateService;

    @Autowired
    private ContractTemplateRepository contractTemplateRepository;

    @Autowired
    private FileAddressController fileAddressController;

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
        for (int i = 0; i < 50; i++) {
            buyers.add(new OnlineUser(i, i,
                    "09120000000",
                    "test-buyer" + i + "-client-id",
                    "test-buyer" + i + "-client-name"));
        }
        logger.info("Finished creating dummy users.");
    }

    @BeforeEach
    public void setupBeforeEach() {
        BookkeepingMocks.setupMockBookkeepingResponse(mockServer);
        ProfilesMocks.setupMockProfileResponse(mockServer);
        MessageMocks.setupMockMessageResponse(mockServer);
        ScoringCommunicatorMocks.setupMockScoringCommunicatorResponse(mockServer);
    }

    @AfterEach
    public void setupAfterEach() {
        schedulingSwitch.setEnabled(true);
    }

    @Test
    @DirtiesContext
    public void testPinnedSuggestions() {
        schedulingSwitch.setEnabled(false);
        Random random = new Random(System.currentTimeMillis());
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
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            new TransactionTemplate(this.transactionManager)
                    .executeWithoutResult(status -> createSuggestionForTemplate(getAllTemplatesOfSeller().get(0),
                            contractTemplateService,
                            contractTemplateController,
                            suggestionController,
                            context));
        });
        logger.info("Finished creating buyer suggestions.");

        logger.info("STAGE-3: Reading all suggestions for seller and pinning 10 random suggestions");
        changeCurrentUser(sellers.get(0), context);
        List<SuggestionForSellerResponse> suggestionForSellerResponses =
                suggestionController.readAllForSeller(vehicleTemplateId, 1, 50, false, null);
        List<Long> pinnedSuggestionIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SuggestionForSellerResponse randomSuggestion;
            do {
                randomSuggestion =
                        suggestionForSellerResponses.get(random.nextInt(suggestionForSellerResponses.size()));
            } while (pinnedSuggestionIds.contains(randomSuggestion.getId()));
            SuggestionForSellerResponse finalRandomSuggestion = randomSuggestion;
            new TransactionTemplate(transactionManager)
                    .executeWithoutResult(status ->
                            suggestionController.pin(finalRandomSuggestion.getId(), true)
                    );
            pinnedSuggestionIds.add(randomSuggestion.getId());
        }
        logger.info("Finished pinning suggestions.");

        logger.info("STAGE-4: Verifying pinned suggestions");
        suggestionForSellerResponses = suggestionController.readAllForSeller(vehicleTemplateId,
                1, 10, false, null);

        assertTrue(suggestionForSellerResponses
                        .stream().map(SuggestionForSellerResponse::getId).allMatch(pinnedSuggestionIds::contains),
                "Expecting all 10 pinned suggestions to be present at first page");
        logger.info("Finished verification of pinned suggestions");

    }

    @Test
    @DirtiesContext
    public void testSortedSuggestions() {
        schedulingSwitch.setEnabled(false);
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
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            new TransactionTemplate(this.transactionManager)
                    .executeWithoutResult(status -> createSuggestionForTemplate(getAllTemplatesOfSeller().get(0),
                            contractTemplateService,
                            contractTemplateController,
                            suggestionController,
                            context));
        });
        logger.info("Finished creating buyer suggestions.");


        logger.info("STAGE-3: Verifying sorted by score suggestions");
        changeCurrentUser(sellers.get(0), context);
        List<SuggestionForSellerResponse> suggestionForSellerResponses =
                suggestionController.readAllForSeller(vehicleTemplateId, 1, 50, false, SortBy.SCORE);

        List<SuggestionForSellerResponse> sorted = suggestionForSellerResponses.stream().sorted((o1, o2) -> {
            if (o1.getBuyer().getScore() < o2.getBuyer().getScore()) {
                return 1;
            } else if (o1.getBuyer().getScore().equals(o2.getBuyer().getScore())) {
                return 0;
            } else {
                return -1;
            }
        }).collect(Collectors.toList());

        assertIterableEquals(sorted, suggestionForSellerResponses, "expecting to be ordered by score");

        logger.info("Finished verification of sorted suggestions");

        logger.info("STAGE-4: Verifying sorted by price");
        suggestionForSellerResponses =
                suggestionController.readAllForSeller(vehicleTemplateId, 1, 50, false, SortBy.PRICE);
        sorted = suggestionForSellerResponses.stream().sorted((o1, o2) -> {
                    if (o1.getPrice() < o2.getPrice()) {
                        return 1;
                    } else if (Objects.equals(o1.getPrice(), o2.getPrice())) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
        ).collect(Collectors.toList());
        assertIterableEquals(sorted, suggestionForSellerResponses, "Expecting to be sorted by price");
        logger.info("Finished verifying sorted by price");
    }

    @Test
    @DirtiesContext
    public void testSuggestionStatus() {
        schedulingSwitch.setEnabled(false);
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
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            new TransactionTemplate(this.transactionManager)
                    .executeWithoutResult(status -> {
                        ContractTemplate contractTemplate = contractTemplateService.find(vehicleTemplateId);
                        Suggestion suggestion = new Suggestion();
                        suggestion.setContractTemplate(contractTemplate);
                        suggestion.setPrice((long) generateRandomNumber(100_000, 999_999_999));
                        suggestion.setContractTemplate(contractTemplate);
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        suggestion.setUpdateDate(timestamp);
                        suggestion.setSuggestionState(randomEnumOf(SuggestionState.class));
                        suggestion.setCreateDate(timestamp);
                        suggestion.setSuggestionStateDate(timestamp);
                        suggestion.setBuyerId(buyer.getUserId());
                        if (logger.isDebugEnabled()) {
                            GetContractTemplateResponse contractTemplateResponse =
                                    contractTemplateController.get(vehicleTemplateId);

                            logger.debug("Creating suggestion request for template: {}.\nBuyer: {}\nSeller: {}",
                                    contractTemplate,
                                    context.getBean("getOnlineUser").toString(),
                                    contractTemplateResponse.getSeller());
                        }
                        suggestionRepository.save(suggestion);
                    });
        });
        logger.info("Finished creating buyer suggestions.");

        logger.info("STAGE-3: verifying suggestion states in getAllTemplatesOfSeller");
        changeCurrentUser(sellers.get(0), context);
        assertFalse(suggestionController.readAllForSeller(
                vehicleTemplateId,
                1,
                50,
                false,
                SortBy.NONE
                ).stream().anyMatch(suggestionForSellerResponse ->
                        suggestionForSellerResponse.getSuggestionState() == SuggestionState.DENIED),
                "Result should not contain any suggestion with DENIED state.");
        logger.info("Finished verifying states.");
    }

    @Test
    @DisplayName("Create template make suggestion and read for buyer and verify it returns latest denied suggestion" +
            " when there is no unattended suggestion for a template contract.")
    @DirtiesContext
    public void testReadAllForBuyer() {
        schedulingSwitch.setEnabled(false);
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
        buyers.forEach(buyer -> {
            changeCurrentUser(buyer, context);
            new TransactionTemplate(this.transactionManager)
                    .executeWithoutResult(status -> createSuggestionForTemplate(getAllTemplatesOfSeller().get(0),
                            contractTemplateService,
                            contractTemplateController,
                            suggestionController,
                            context));
        });
        logger.info("Finished creating buyer suggestions.");

        logger.info("STAGE-3: Denying suggestions for buyer 10");
        changeCurrentUser(sellers.get(0), context);
        List<Suggestion> suggestions =
                suggestionRepository.findByContractTemplateAndBuyerIdOrderByCreateDateDesc(
                        contractTemplateRepository.findById(vehicleTemplateId).orElseThrow(),
                        buyers.get(10).getUserId());
        suggestions.stream().map(Suggestion::getId).forEach(suggestionController::deny);
        logger.info("Finished denying suggestion");

        logger.info("STAGE-4: looking for denied suggestion in read all for buyer 10");
        changeCurrentUser(buyers.get(10), context);
        SuggestionForBuyerResponse suggestionForBuyer = suggestionController.readForBuyer(vehicleTemplateId);
        assertEquals(SuggestionState.DENIED, suggestionForBuyer.getSuggestionState());
        logger.info("Finished verification for buyer 10");

        logger.info("STAGE-5: reading suggestion for other buyers");
        buyers.stream().filter(onlineUser ->
                onlineUser.getUserId() != buyers.get(10).getUserId()).forEach(onlineUser -> {
            changeCurrentUser(onlineUser, context);
            assertEquals(SuggestionState.UNATTENDED,
                    suggestionController.readForBuyer(vehicleTemplateId).getSuggestionState());
        });
        logger.info("Finished validating rest of the buyers");
        logger.info("STAGE-6: removing a suggestion");
        changeCurrentUser(buyers.get(11), context);
        suggestionForBuyer = suggestionController.readForBuyer(vehicleTemplateId);
        suggestionController.delete(suggestionForBuyer.getId());
        assertThrows(SuggestionNotFoundException.class, () ->
                suggestionController.readForBuyer(vehicleTemplateId));
        //Trying to remove other users suggestions (user 10 suggestion with uer 11)
        assertThrows(IncompatibleValueException.class, () ->
                suggestions.stream().map(Suggestion::getId).forEach(suggestionController::delete));
        logger.info("Finished removing suggestion");


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
