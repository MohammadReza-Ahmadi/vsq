package com.vosouq.contract.service;

import com.vosouq.contract.model.City;
import com.vosouq.contract.model.Province;

import java.util.List;

public interface GeographicInformationService {
    List<Province> getProvinces();
    List<City> getCities(Long provinceId);
}
