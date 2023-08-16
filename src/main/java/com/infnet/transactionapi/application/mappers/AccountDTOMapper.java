package com.infnet.transactionapi.application.mappers;

import com.infnet.transactionapi.application.DTO.AccountDTO;
import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOMapper {
    private final ModelMapper modelMapper;

    public AccountDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountDTO toDTO(AccountDomain accountDomain) {
        return modelMapper.map(accountDomain, AccountDTO.class);
    }

    public AccountDomain toDomain(AccountDTO accountDTO) {
        return modelMapper.map(accountDTO, AccountDomain.class);
    }
}
