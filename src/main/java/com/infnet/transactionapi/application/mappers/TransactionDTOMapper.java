package com.infnet.transactionapi.application.mappers;

import com.infnet.transactionapi.application.DTO.TransactionDTO;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionDTOMapper {
    private final ModelMapper modelMapper;

    public TransactionDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionDTO toDTO(TransactionDomain transactionDomain) {
        return modelMapper.map(transactionDomain, TransactionDTO.class);
    }

    public TransactionDomain toDomain(TransactionDTO transactionDTO) {
        return modelMapper.map(transactionDTO, TransactionDomain.class);
    }
}
