package com.infnet.transactionapi.domain.repositories;
import com.infnet.transactionapi.domain.domainModels.TransactionDomain;

import java.util.List;

public interface TransactionRepository {
    List<TransactionDomain> findAll();
}
