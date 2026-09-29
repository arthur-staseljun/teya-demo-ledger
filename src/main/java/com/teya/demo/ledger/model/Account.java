package com.teya.demo.ledger.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Account {
    private Long id;
    private AccountStatus accountStatus;
    private Currency currency;
    private BigDecimal amount;
    private Instant createdAt;

    public enum AccountStatus {
        ACTIVE, INACTIVE
    }

    public enum Currency {
        EUR
    }

    public Account(Long id) {
        this.id = id;
        this.accountStatus = AccountStatus.ACTIVE;
        this.currency = Currency.EUR;
        this.amount = BigDecimal.ZERO;
        this.createdAt = Instant.now();
    }
}
