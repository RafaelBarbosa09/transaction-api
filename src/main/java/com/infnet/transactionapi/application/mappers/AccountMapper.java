package com.infnet.transactionapi.application.mappers;

import com.infnet.transactionapi.domain.entities.AccountDomain;
import com.infnet.transactionapi.infrastructure.models.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountDomain toEntity(Account accountModel) {
        return modelMapper.map(accountModel, AccountDomain.class);
    }

    public Account toModel(AccountDomain accountDomain) {
        return modelMapper.map(accountDomain, Account.class);
    }
}
