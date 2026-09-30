package com.teya.demo.ledger.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CurrencyTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t"})
    void parseDefaultsToEurWhenMissingOrBlank(String currencyCode) {
        assertThat(Currency.parse(currencyCode)).isEqualTo(Currency.EUR);
    }

    @ParameterizedTest
    @ValueSource(strings = {"EUR", "eur", "Eur"})
    void parseIsCaseInsensitive(String currencyCode) {
        assertThat(Currency.parse(currencyCode)).isEqualTo(Currency.EUR);
    }

    @Test
    void parseRejectsUnknownCurrency() {
        assertThatThrownBy(() -> Currency.parse("USD"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
