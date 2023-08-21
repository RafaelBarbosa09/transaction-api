package com.infnet.transactionapi.application.services;

import com.infnet.transactionapi.application.DTO.SellerDTO;
import com.infnet.transactionapi.application.mappers.SellerDTOMapper;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.domain.repositories.SellerRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;

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

    public SellerDTO createSeller(SellerDTO sellerDTO) {
        SellerDomain seller = mapper.toDomain(sellerDTO);
        return mapper.toDTO(sellerRepository.save(seller));
    }

    public SellerDTO updateSeller(Long id, SellerDTO sellerDTO) {
        SellerDomain seller = sellerRepository.findById(id);
        seller.setName(sellerDTO.getName());
        return mapper.toDTO(sellerRepository.update(seller));
    }

    public SellerDTO getSellerById(Long id) {
        SellerDomain seller = sellerRepository.findById(id);
        if (Objects.isNull(seller)) {
            throw new NotFoundException("not found");
        }
        return mapper.toDTO(seller);
    }
}
