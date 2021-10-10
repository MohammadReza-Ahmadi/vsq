package com.vosouq.contract.repository;

import com.vosouq.contract.model.FileAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAddressRepository extends JpaRepository<FileAddress, Long> {

}