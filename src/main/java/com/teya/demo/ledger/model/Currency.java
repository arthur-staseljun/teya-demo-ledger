package com.teya.demo.ledger.model;

import java.util.Arrays;

public enum Currency {
    EUR;

    public static Currency parse(String currencyCode) {
        if (currencyCode == null || currencyCode.isBlank()) {
            return EUR;
        }
        return Arrays.stream(values())
                .filter(currency -> currency.name().equalsIgnoreCase(currencyCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown currency: " + currencyCode));
    }
}
