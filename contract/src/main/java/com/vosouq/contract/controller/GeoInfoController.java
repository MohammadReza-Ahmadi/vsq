package com.vosouq.contract.controller;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.contract.model.City;
import com.vosouq.contract.model.Province;
import com.vosouq.contract.service.GeographicInformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@VosouqRestController
@RequestMapping("/geo")
public class GeoInfoController {

    private final GeographicInformationService geoService;

    public GeoInfoController(GeographicInformationService geoService) {
        this.geoService = geoService;
    }

    @GetMapping("/province")
    public List<Province> getProvinces(){
        return geoService.getProvinces();
    }

    @GetMapping("/city/{provinceId}")
    public List<City> getCities(@PathVariable Long provinceId){
        return geoService.getCities(provinceId);
    }
}
