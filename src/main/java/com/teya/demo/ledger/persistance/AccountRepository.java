package com.teya.demo.ledger.persistance;

import com.teya.demo.ledger.model.Account;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class AccountRepository {

    private final ConcurrentMap<Long, Account> store = new ConcurrentHashMap<>();

    public Account save(Account account) {
        store.put(account.getId(), account);
        return account;
    }

    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Account applyBalanceChange(Long accountId, BigDecimal amount) {
        return store.compute(accountId, (id, account) -> {
            if (account == null) {
                throw new IllegalArgumentException("Account not found.");
            }
            BigDecimal newBalance = account.getBalance().add(amount);
            if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Not sufficient founds");
            }
            account.setBalance(newBalance);
            account.setUpdatedAt(Instant.now());
            return account;
        });
    }

}
