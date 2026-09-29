package com.teya.demo.ledger.persistance;

import com.teya.demo.ledger.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class TransactionRepository {

    private final ConcurrentMap<UUID, Transaction> store = new ConcurrentHashMap<>();

    public UUID generateTransactionId() {
        return UUID.randomUUID();
    }

    public Transaction save(UUID id, Transaction transaction) {
        store.put(id, transaction);
        return transaction;
    }

    public Optional<Transaction> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Transaction> findAll() {
        return List.copyOf(store.values());
    }

    public void deleteById(UUID id) {
        store.remove(id);
    }
}
