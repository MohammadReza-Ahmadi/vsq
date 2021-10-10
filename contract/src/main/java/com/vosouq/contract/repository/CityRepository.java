package com.vosouq.contract.repository;

import com.vosouq.contract.model.City;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findByProvinceId(Long provinceId, Sort sort);
}
