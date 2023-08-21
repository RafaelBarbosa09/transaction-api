package com.infnet.transactionapi.domain.repositories;

import com.infnet.transactionapi.domain.domainModels.SellerDomain;

import java.util.List;

public interface SellerRepository {
    SellerDomain findByName(String name);
    List<SellerDomain> findAll();
    SellerDomain findById(Long id);
    SellerDomain save(SellerDomain seller);
    SellerDomain update(SellerDomain seller);
}
