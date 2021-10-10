package com.vosouq.contract.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vosouq.contract.model.City;
import com.vosouq.contract.model.Config;
import com.vosouq.contract.model.Province;
import com.vosouq.contract.repository.CityRepository;
import com.vosouq.contract.repository.ConfigRepository;
import com.vosouq.contract.repository.ProvinceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class DataInitializer {
    private final ProvinceRepository provinceRepository;
    private final CityRepository cityRepository;
    private final ConfigRepository configRepository;
    private final ObjectMapper objectMapper;

    public DataInitializer(ProvinceRepository provinceRepository,
                           CityRepository cityRepository,
                           ConfigRepository configRepository,
                           ObjectMapper objectMapper) {
        this.provinceRepository = provinceRepository;
        this.cityRepository = cityRepository;
        this.configRepository = configRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initializeGeographicInformation() {
        log.info("Checking required geographic information.");
        configRepository.findByName(Config.GEOGRAPHIC_INFORMATION_INITIALIZED).ifPresentOrElse(
                config -> {
                    if (config.getValue() != null && !config.getValue().equals("true")) {
                        importFromJson();
                    }
                },
                this::importFromJson);
    }

    private void importFromJson() {
        log.info("Failed to verify geographic information presence. Importing form static resources.");
        try {
            List<City> cities = objectMapper.readValue(
                    DataInitializer.class.getClassLoader().getResourceAsStream("static/cities.json"),
                    new TypeReference<>() {
                    });
            List<Province> provinces = objectMapper.readValue(
                    DataInitializer.class.getClassLoader().getResourceAsStream("static/province.json"),
                    new TypeReference<>() {
                    }
            );

            provinceRepository.saveAll(provinces);
            cityRepository.saveAll(cities);
            configRepository.save(
                    new Config(Config.GEOGRAPHIC_INFORMATION_INITIALIZED, "true"));
            log.debug("Finished importing geographic information.");

        } catch (IOException e) {
            log.error("Failed to import geographic data from json resource.", e);
            throw new RuntimeException("Failed to import geographic data from json resource.", e);
        }
    }
}
