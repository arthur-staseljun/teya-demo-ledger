package com.teya.demo.ledger.persistence;

import com.teya.demo.ledger.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository {

    Account save(Account account);

    Optional<Account> findById(Long id);

    Optional<BigDecimal> applyBalanceChange(Long accountId, BigDecimal amount);
}
