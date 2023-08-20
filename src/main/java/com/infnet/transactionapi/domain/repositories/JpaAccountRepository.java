package com.infnet.transactionapi.domain.repositories;

import com.infnet.transactionapi.infrastructure.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountHolder(String accountHolder);
    void deleteById(Long id);
}
