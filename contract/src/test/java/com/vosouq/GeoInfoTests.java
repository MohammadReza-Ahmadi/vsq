package com.vosouq;

import com.vosouq.config.ContractKafkaConfig;
import com.vosouq.config.ContractWireMockConfig;
import com.vosouq.contract.ContractApplication;
import com.vosouq.contract.controller.GeoInfoController;
import com.vosouq.contract.model.City;
import com.vosouq.contract.model.Province;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ContractApplication.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ContractWireMockConfig.class, ContractKafkaConfig.class})
public class GeoInfoTests {

    @Autowired
    GeoInfoController geoInfoController;

    @Test
    void getProvincesTest(){
        List<Province> provinces = geoInfoController.getProvinces();
        assertEquals(31, provinces.size(), "Expecting 31 provinces of Iran");
        assertEquals(4, provinces.get(0).getId(),
                "Expecting the result to be sorter by title (first one should be East Azarbaijan)");
    }

    @Test
    void getCitiesTest(){
        List<Province> provinces = geoInfoController.getProvinces();
        provinces.forEach(
                province -> assertTrue(geoInfoController.getCities(province.getId()).size() > 0,
                        "There must be at least one city registered for each province")
        );

    }
}
