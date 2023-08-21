package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.SellerDTO;
import com.infnet.transactionapi.application.mappers.SellerDTOMapper;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.domain.repositories.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final SellerDTOMapper mapper;

    public SellerService(SellerRepository sellerRepository, SellerDTOMapper mapper) {
        this.sellerRepository = sellerRepository;
        this.mapper = mapper;
    }

    public List<SellerDTO> getAllSellers() {
        List<SellerDomain> sellers = sellerRepository.findAll();
        return sellers.stream().map(mapper::toDTO).toList();
    }
}
