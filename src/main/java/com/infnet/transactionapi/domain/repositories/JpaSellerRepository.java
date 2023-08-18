package com.infnet.transactionapi.domain.repositories;

import com.infnet.transactionapi.infrastructure.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSellerRepository extends JpaRepository<Seller, Long> {
    Seller findByName(String name);
}
