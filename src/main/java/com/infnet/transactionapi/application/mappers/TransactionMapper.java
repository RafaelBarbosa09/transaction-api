package com.infnet.transactionapi.application.mappers;

import com.infnet.transactionapi.domain.domainModels.TransactionDomain;
import com.infnet.transactionapi.infrastructure.entities.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionDomain toDomain(Transaction transactionEntity) {
        return this.modelMapper.map(transactionEntity, TransactionDomain.class);
    };

    public Transaction toEntity(TransactionDomain transactionDomain) {
        return this.modelMapper.map(transactionDomain, Transaction.class);
    };
}
