package com.teya.demo.ledger.controller;

import com.teya.demo.ledger.exception.classification.ErrorClassification;
import com.teya.demo.ledger.exception.classification.LedgerServiceException;
import com.teya.demo.ledger.model.Currency;
import com.teya.demo.ledger.model.Transaction;
import com.teya.demo.ledger.model.request.CreateTransactionRequest;
import com.teya.demo.ledger.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/{accountId}/transactions")
    public Transaction transact(@PathVariable String accountId,
                                @RequestBody CreateTransactionRequest createTransactionRequest) {
        Long id = parse(accountId);
        BigDecimal amount = parseAmount(createTransactionRequest.amount());
        Currency currency = parseCurrency(createTransactionRequest.currency());
        return transactionService.createTransaction(id, amount, currency);
    }

    @GetMapping("/{accountId}/transactions")
    public List<Transaction> getTransactions(@PathVariable String accountId) {
        return transactionService.getAllTransactions(parse(accountId));
    }

    private Long parse(String accountId) {
        try {
            return Long.parseLong(accountId);
        } catch (NumberFormatException ex) {
            throw new LedgerServiceException(ErrorClassification.ACCOUNT_ID_PARSE_ERROR);
        }
    }

    private Currency parseCurrency(String currencyCode) {
        try {
            return Currency.parse(currencyCode);
        } catch (IllegalArgumentException ex) {
            throw new LedgerServiceException(ErrorClassification.CURRENCY_MISMATCH);
        }
    }

    private BigDecimal parseAmount(String amountString) {
        if (amountString == null || amountString.isBlank()) {
            throw new LedgerServiceException(ErrorClassification.AMOUNT_PARSE_ERROR);
        }
        try {
            return new BigDecimal(amountString);
        } catch (NumberFormatException | ArithmeticException ex) {
            throw new LedgerServiceException(ErrorClassification.AMOUNT_PARSE_ERROR);
        }
    }
}
