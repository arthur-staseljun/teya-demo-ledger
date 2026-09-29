package com.teya.demo.ledger.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Transaction {
    private UUID transactionId;
    private Long accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    private Instant createdAt;

    public enum TransactionType {
        DEBIT,
        CREDIT
    }

    public Transaction(UUID transactionId, Long accountId,
                       TransactionType transactionType, BigDecimal amount, BigDecimal balanceAfter) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.createdAt = Instant.now();
    }
}
