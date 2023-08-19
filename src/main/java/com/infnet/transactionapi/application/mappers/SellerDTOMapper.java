package com.infnet.transactionapi.application.mappers;

import com.infnet.transactionapi.application.DTO.SellerDTO;
import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SellerDTOMapper {
    private final ModelMapper mapper;

    public SellerDTOMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public SellerDTO toDTO(SellerDTO sellerDTO) {
        if (Objects.isNull(sellerDTO)) {
            return null;
        }

        return mapper.map(sellerDTO, SellerDTO.class);
    }

    public SellerDomain toDomain(SellerDTO sellerDTO) {
        if (Objects.isNull(sellerDTO)) {
            return null;
        }

        return mapper.map(sellerDTO, SellerDomain.class);
    }
}
