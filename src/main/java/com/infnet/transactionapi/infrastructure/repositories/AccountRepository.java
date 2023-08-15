package com.infnet.transactionapi.infrastructure.repositories;

import com.infnet.transactionapi.infrastructure.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
