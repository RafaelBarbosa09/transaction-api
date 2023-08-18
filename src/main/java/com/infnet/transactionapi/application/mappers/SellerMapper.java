package com.infnet.transactionapi.application.mappers;

import com.infnet.transactionapi.domain.domainModels.SellerDomain;
import com.infnet.transactionapi.infrastructure.entities.Seller;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SellerMapper {
    private final ModelMapper mapper;

    public SellerMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public SellerDomain toDomain(Seller seller) {
        if (Objects.isNull(seller)) {
            return null;
        }

        return mapper.map(seller, SellerDomain.class);
    }

    public Seller toEntity(SellerDomain seller) {
        if (Objects.isNull(seller)) {
            return null;
        }

        return mapper.map(seller, Seller.class);
    }
}
