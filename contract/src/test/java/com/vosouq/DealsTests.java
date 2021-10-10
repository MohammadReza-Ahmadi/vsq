package com.vosouq;

import com.vosouq.commons.model.OnlineUser;
import com.vosouq.config.ContractKafkaConfig;
import com.vosouq.config.ContractWireMockConfig;
import com.vosouq.contract.ContractApplication;
import com.vosouq.contract.controller.DealController;
import com.vosouq.contract.controller.dto.ActionStateFilterResponse;
import com.vosouq.contract.model.ActionState;
import com.vosouq.contract.model.RecentDealModel;
import com.vosouq.contract.repository.DealHistoryRepository;
import com.vosouq.contract.service.ContractService;
import com.vosouq.util.ContractTestUtil;
import com.vosouq.util.DealHistoryTestUtil;
import com.vosouq.util.RandomGeneratorUtility;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ContractApplication.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ContractWireMockConfig.class, ContractKafkaConfig.class})
public class DealsTests {

    private static final Logger logger = LoggerFactory.getLogger(DealsTests.class);
    private static final long SELLER_ID = 100L;

    private static final List<OnlineUser> buyers = new ArrayList<>();
    private static final List<OnlineUser> sellers = new ArrayList<>();

    @Autowired
    GenericApplicationContext context;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    DealController dealController;

    @Autowired
    ContractService contractService;

    @Autowired
    DealHistoryRepository dealHistoryRepository;

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
    void getAvailableFiltersTest() {
        List<ActionStateFilterResponse> availableFilters = dealController.getAvailableFilters();
        assertEquals(7, availableFilters.size(), "Number of available");
        assertEquals(availableFilters.get(0).getKey(), "WAITING_FOR_SIGN");
        assertEquals(availableFilters.get(6).getKey(), "END_OF_CONTRACT");
    }

    @RepeatedTest(5)
    void findDealsByFiltersTest() {
        new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
                    for (int i = 0; i < 1000; i++)
                        dealHistoryRepository.save(DealHistoryTestUtil.createDealHistory(
                                buyers.get(RandomGeneratorUtility.generateRandomNumber(0, 4)).getUserId(),
                                sellers.get(0).getUserId()));
                }
        );
        List<RecentDealModel> recentDeals = contractService.getRecentDeals(
                SELLER_ID,
                List.of(ActionState.PAID, ActionState.WAITING_FOR_PAYMENT),
                1,
                100);
        boolean allMatch = recentDeals.stream().allMatch(recentDealModel -> {
            if (recentDealModel.getActionState() != ActionState.PAID &&
                    recentDealModel.getActionState() != ActionState.WAITING_FOR_PAYMENT) {
                logger.debug(recentDealModel.toString());
            }
            return recentDealModel.getActionState() == ActionState.PAID ||
                    recentDealModel.getActionState() == ActionState.WAITING_FOR_PAYMENT;
        });
        assertTrue(allMatch, "Expecting to find only PAID and WAITING_FOR_PAYMENT deals");
    }

    @RepeatedTest(5)
    @DirtiesContext
    void dealsFilterTest() {
        ContractTestUtil.changeCurrentUser(sellers.get(0), context);
        assertEquals(0, dealController.getAll(Collections.emptyList(), 1, 100).size(),
                "Expecting to find no deals");

        logger.info("STAGE-1: Creating 200 dummy deal histories...");
        for (int i = 0; i < 200; i++)
            new TransactionTemplate(transactionManager).executeWithoutResult(status -> {
                dealHistoryRepository.save(DealHistoryTestUtil.createDealHistory(
                        buyers.get(RandomGeneratorUtility.generateRandomNumber(0, 4)).getUserId(),
                        sellers.get(0).getUserId()));
            });
        logger.debug("All deals history: {}", dealHistoryRepository.findAll().size());
        logger.info("Finished creating dummy deal histories.");

        Set<ActionState> filters = new HashSet<>();
        int actionStatesCount = ActionState.values().length;
        logger.info("STAGE-2: Creating random filter selections and verifying the filters are applied correctly...");
        for (int i = 0; i < RandomGeneratorUtility.generateRandomNumber(0, actionStatesCount); i++) {
            filters.add(ActionState.values()[RandomGeneratorUtility.generateRandomNumber(0, actionStatesCount)]);
        }
        assertTrue(
                dealController.getAll(new ArrayList<>(filters), 1, 100)
                        .stream().allMatch(recentDealResponse ->
                        filters.isEmpty() || filters.contains(recentDealResponse.getActionState()))
                , "Expecting to only see deals with an ActionStatus that is included in filter list");
        logger.info("Finished STAGE-2");

        logger.info("STAGE-3: Creating empty filter selection...");
        assertEquals(200, dealController.getAll(Collections.emptyList(), 1, 200).size(),
                "Expecting to find all 200 generated deals");
        logger.info("Finished testing filters with empty selection");
    }
}
