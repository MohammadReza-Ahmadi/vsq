package com.vosouq.contract.repository;

import com.vosouq.contract.model.Config;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfigRepository extends JpaRepository<Config, Long> {
    Optional<Config> findByName(String name);
}
