package com.infnet.transactionapi.domain.repositories;

import com.infnet.transactionapi.infrastructure.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTransactionRepository extends JpaRepository<Transaction, Long> {

}
