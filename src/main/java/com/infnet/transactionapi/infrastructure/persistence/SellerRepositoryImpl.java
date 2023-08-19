package com.infnet.transactionapi.infrastructure.persistence;

import com.infnet.transactionapi.application.mappers.SellerMapper;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.domain.repositories.JpaSellerRepository;
import com.infnet.transactionapi.domain.repositories.SellerRepository;
import com.infnet.transactionapi.infrastructure.entities.Seller;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SellerRepositoryImpl implements SellerRepository {
    private final JpaSellerRepository repository;
    private final SellerMapper mapper;

    public SellerRepositoryImpl(JpaSellerRepository repository, SellerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public SellerDomain findByName(String name) {
        Seller seller = repository.findByName(name);
        return mapper.toDomain(seller);
    }

    @Override
    public List<SellerDomain> findAll() {
        List<Seller> sellers = repository.findAll();
        return sellers.stream().map(mapper::toDomain).toList();
    }

    @Override
    public SellerDomain findById(Long id) {
        return null;
    }

    @Override
    public SellerDomain save(SellerDomain seller) {
        Seller sellerEntity = mapper.toEntity(seller);
        Seller sellerSaved = repository.save(sellerEntity);
        return mapper.toDomain(sellerSaved);
    }
}