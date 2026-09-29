package com.teya.demo.ledger.controller;

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
@RequestMapping("/api/accounts/{accountIdString}/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public Transaction transact(@PathVariable String accountIdString,
                                @RequestBody CreateTransactionRequest createTransactionRequest) {
        Long accountId = parse(accountIdString);
        BigDecimal amount = parseAmount(createTransactionRequest.amount());
        Currency currency = Currency.parse(createTransactionRequest.currency());
        return transactionService.createTransaction(accountId, amount, currency);
    }

    @GetMapping
    public List<Transaction> getTransactions(@PathVariable String accountIdString) {
        return transactionService.getAllTransactions(parse(accountIdString));
    }

    private Long parse(String accountIdString) {
        try {
            return Long.parseLong(accountIdString);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid account id: " + accountIdString);
        }
    }

    private BigDecimal parseAmount(String amountString) {
        try {
            return new BigDecimal(amountString);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid amount string: " + amountString);
        }
    }
}
