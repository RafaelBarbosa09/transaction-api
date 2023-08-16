package com.infnet.transactionapi.application.mappers;

import com.infnet.transactionapi.domain.domainModels.AccountDomain;
import com.infnet.transactionapi.infrastructure.entities.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountDomain toDomain(Account accountEntity) {
        return modelMapper.map(accountEntity, AccountDomain.class);
    }

    public Account toEntity(AccountDomain accountDomain) {
        return modelMapper.map(accountDomain, Account.class);
    }
}
