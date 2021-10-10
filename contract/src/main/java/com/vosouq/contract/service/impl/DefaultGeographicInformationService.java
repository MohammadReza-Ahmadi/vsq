package com.vosouq.contract.service.impl;

import com.vosouq.contract.model.City;
import com.vosouq.contract.model.Province;
import com.vosouq.contract.repository.CityRepository;
import com.vosouq.contract.repository.ProvinceRepository;
import com.vosouq.contract.service.GeographicInformationService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGeographicInformationService implements GeographicInformationService {

    private final CityRepository cityRepository;
    private final ProvinceRepository provinceRepository;

    public DefaultGeographicInformationService(CityRepository cityRepository,
                                               ProvinceRepository provinceRepository) {
        this.cityRepository = cityRepository;
        this.provinceRepository = provinceRepository;
    }

    @Override
    public List<Province> getProvinces() {
        return provinceRepository.findAll(Sort.by("title"));
    }

    @Override
    public List<City> getCities(Long provinceId) {
        return cityRepository.findByProvinceId(provinceId, Sort.by("title"));
    }
}
