package com.vosouq;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.config.ContractKafkaConfig;
import com.vosouq.config.ContractWireMockConfig;
import com.vosouq.contract.ContractApplication;
import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.model.*;
import com.vosouq.contract.service.ContractTemplateService;
import com.vosouq.contract.service.FileAddressService;
import com.vosouq.contract.service.feign.BookkeepingServiceClient;
import com.vosouq.contract.service.feign.ProfileServiceClient;
import com.vosouq.contract.service.feign.ScoringCommunicatorClient;
import com.vosouq.mock.BookkeepingMocks;
import com.vosouq.mock.ProfilesMocks;
import com.vosouq.mock.ScoringCommunicatorMocks;
import com.vosouq.util.ContractTestUtil;
import com.vosouq.util.RandomGeneratorUtility;
import com.vosouq.util.TestMultipartFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.vosouq.util.ContractTestUtil.fillGenericDataOf;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest(classes = ContractApplication.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ContractWireMockConfig.class, ContractKafkaConfig.class})
public class TemplateContractBasicTests {

    private static final Logger logger = LoggerFactory.getLogger(TemplateContractBasicTests.class);

    @Autowired
    private GenericApplicationContext context;

    @Autowired
    private WireMockServer mockServer;

    @Autowired
    private BookkeepingServiceClient bookkeepingServiceClient;

    @Autowired
    private ProfileServiceClient profileServiceClient;

    @Autowired
    private ScoringCommunicatorClient scoringCommunicatorClient;

    @Autowired
    private ContractTemplateService contractTemplateService;

    @Autowired
    private FileAddressService fileAddressService;

    @Test
    void bookkeepingServiceEndpointAvailabilityTest() {
        bookkeepingServiceClient.settle(10L, 20L);
    }

    @Test
    void profileServiceEndpointAvailabilityTest() {
        User user = profileServiceClient.findById(1L);
        assertNotNull("User must not be null", user);
        assertEquals("UserId must be 1", Long.valueOf(1), user.getUserId());
        assertEquals("Name must not be user-1", "user-1", user.getName());
        assertEquals("http://mock.com/img.jpg", user.getImageUrl());
    }

    @Test
    void createTemplateContract() {
        ContractTestUtil.changeCurrentUser(new OnlineUser(
                1L,
                0L,
                "091200000",
                "test-client-id",
                "test-client-name"), context);
        VehicleContractTemplate vehicleContractTemplate = new VehicleContractTemplate(
                1974,
                200000,
                Gearbox.AUTOMATIC,
                Fuel.GASOLINE,
                BodyStatus.NOT_COLORED,
                Color.BLACK,
                true
        );
        vehicleContractTemplate.setNumberOfRepeats(1);
        vehicleContractTemplate.setRepeatable(false);
        vehicleContractTemplate.setFavorite(false);
        vehicleContractTemplate.setSellerId(1L);
        vehicleContractTemplate.setPrice(50000000L);
        vehicleContractTemplate.setViewable(true);
        vehicleContractTemplate.setTitle("Chevy Nova");
        vehicleContractTemplate.setCity("Half moon bay");
        vehicleContractTemplate.setProvince("California");
        VehicleContractTemplate contractTemplate = contractTemplateService.saveVehicle(vehicleContractTemplate);
        assertNotNull(contractTemplate);
        assertNotNull(contractTemplate.getId());
        assertEquals("Chevy Nova", contractTemplate.getTitle());
    }

    @Test
    void createTemplateContractCityAndProvinceValidationCheck() {
        ContractTestUtil.changeCurrentUser(new OnlineUser(
                1L,
                0L,
                "091200000",
                "test-client-id",
                "test-client-name"), context);
        VehicleContractTemplate vehicleContractTemplate = new VehicleContractTemplate(
                1974,
                200000,
                Gearbox.AUTOMATIC,
                Fuel.GASOLINE,
                BodyStatus.NOT_COLORED,
                Color.BLACK,
                true
        );
        vehicleContractTemplate.setNumberOfRepeats(1);
        vehicleContractTemplate.setRepeatable(false);
        vehicleContractTemplate.setFavorite(false);
        vehicleContractTemplate.setSellerId(1L);
        vehicleContractTemplate.setPrice(50000000L);
        vehicleContractTemplate.setViewable(true);
        vehicleContractTemplate.setTitle("Chevy Nova");
        assertThrows(ConstraintViolationException.class,
                () -> contractTemplateService.saveVehicle(vehicleContractTemplate));
    }

    @Test
    void priceValidationForContractTemplatesTest() {
        OnlineUser user = new OnlineUser(
                1L,
                0L,
                "091200000",
                "test-client-id",
                "test-client-name");
        ContractTestUtil.changeCurrentUser(user, context);
        logger.info("STAGE-1: Testing price validation for vehicle template...");
        VehicleContractTemplate vehicleContractTemplate = new VehicleContractTemplate();
        fillGenericDataOf(vehicleContractTemplate,
                List.of(generateImage(user.getUserId(), FileType.PRODUCT_IMAGE)),
                user.getUserId()
        );
        vehicleContractTemplate.setManufactureYear(1988);
        vehicleContractTemplate.setUsage(0);
        assertThrows(IncompatibleValueException.class,
                () -> contractTemplateService.saveVehicle(vehicleContractTemplate),
                "Expecting to throw exception for missing price value");
        vehicleContractTemplate.setPrice(1_000_000L);
        contractTemplateService.saveVehicle(vehicleContractTemplate);
        logger.info("Finished testing validation for vehicle template.");

        logger.info("STAGE-2: Testing price validation for commodity template...");
        CommodityContractTemplate commodityContractTemplate = new CommodityContractTemplate();
        fillGenericDataOf(commodityContractTemplate,
                List.of(generateImage(user.getUserId(), FileType.PRODUCT_IMAGE)),
                user.getUserId());
        commodityContractTemplate.setQuantity(10);
        commodityContractTemplate.setPricePerUnit(1000L);
        assertThrows(IncompatibleValueException.class,
                () -> contractTemplateService.saveCommodity(commodityContractTemplate));
        commodityContractTemplate.setPrice(10_000L);
        contractTemplateService.saveCommodity(commodityContractTemplate);
        logger.info("Finished testing validation for commodity.");

        logger.info("STAGE-3: Testing price validation for service template...");
        ServiceContractTemplate serviceContractTemplate = new ServiceContractTemplate();
        fillGenericDataOf(serviceContractTemplate,
                List.of(generateImage(user.getUserId(), FileType.PRODUCT_IMAGE)),
                user.getUserId());
        contractTemplateService.saveService(serviceContractTemplate);
        logger.info("Finished testing validation");

    }

    @Test
    void scoringCommunicatorAvailabilityTest() {
        List<UserScore> usersScores = scoringCommunicatorClient.getUsersScores(List.of(10L, 20L));
        assertEquals("Expecting two UserScore objects to be available in the list",
                2,
                usersScores.size());
        assertTrue("Expecting one user with id 10", usersScores.stream().anyMatch(userScore ->
                userScore.getUserId() == 10L));
        assertTrue("Expecting one user with id 20", usersScores.stream().anyMatch(userScore ->
                userScore.getUserId() == 20L));
    }

    @BeforeEach
    void setUp() {
        BookkeepingMocks.setupMockBookkeepingResponse(mockServer);
        ProfilesMocks.setupMockProfileResponse(mockServer);
        ScoringCommunicatorMocks.setupMockScoringCommunicatorResponse(mockServer);
    }

    @SuppressWarnings("SameParameterValue")
    private FileAddress generateImage(Long userId, FileType fileType) {
        FileAddress fileAddress = null;
        try {
            Long fileId = fileAddressService.upload(
                    new TestMultipartFile(
                            Objects.requireNonNull(RepeatableTemplateContractTests.class
                                    .getClassLoader()
                                    .getResourceAsStream("images/test.jpg")),
                            RandomGeneratorUtility.generateSynthesizedName(8) + ".jpg"),
                    userId,
                    ContractTestUtil.getAddress(fileType),
                    fileType
            );
            fileAddress = fileAddressService.findAndValidate(fileId, fileType, userId);
            logger.debug("Generated new File with id: {}", fileAddress.getId());
        } catch (IOException e) {
            logger.error("Problem while uploading file.", e);
        }
        return fileAddress;
    }
}
