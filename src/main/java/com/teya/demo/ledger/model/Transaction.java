package com.teya.demo.ledger.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Transaction {
    @Setter
    private UUID transactionId;
    private Long accountId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balanceAfter;
    @Setter
    private Instant createdAt;

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL
    }

    public Transaction(Long accountId, BigDecimal amount, BigDecimal balanceAfter) {
        this.accountId = accountId;
        this.transactionType = amount.compareTo(BigDecimal.ZERO) > 0 ? TransactionType.DEPOSIT : TransactionType.WITHDRAWAL;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }
}
