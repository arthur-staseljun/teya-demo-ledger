package com.teya.demo.ledger.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class Account {
    private Long id;
    private Currency currency;
    @Setter
    private BigDecimal balance;
    private Instant createdAt;
    @Setter
    private Instant updatedAt;

    public Account(Long id) {
        this.id = id;
        this.currency = Currency.EUR;
        this.balance = BigDecimal.ZERO;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
