package com.vosouq.gateway.repository;

import com.vosouq.gateway.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientIdAndClientSecret(String clientId, String clientSecret);

}
