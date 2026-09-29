package com.teya.demo.ledger.persistance;

import com.teya.demo.ledger.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class AccountRepository {

    private final ConcurrentMap<Long, Account> store = new ConcurrentHashMap<>();

    public Account save(Long id, Account account) {
        store.put(id, account);
        return account;
    }

    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Account> findAll() {
        return List.copyOf(store.values());
    }

    public void deleteById(Long id) {
        store.remove(id);
    }
}
