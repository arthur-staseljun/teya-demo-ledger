package com.teya.demo.ledger.service;

import com.teya.demo.ledger.model.Account;
import com.teya.demo.ledger.model.Currency;
import com.teya.demo.ledger.model.Transaction;
import com.teya.demo.ledger.persistance.AccountRepository;
import com.teya.demo.ledger.persistance.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public Transaction createTransaction(Long accountId, BigDecimal amount, Currency currency) {
        Account account = getAccount(accountId);
        if (!account.getCurrency().equals(currency)) {
            throw new IllegalArgumentException("Account currency do not match");
        }
        Account updated = accountRepository.applyBalanceChange(accountId, amount);
        return transactionRepository.save(new Transaction(accountId, amount, updated.getBalance()));
    }

    public List<Transaction> getAllTransactions(Long accountId) {
        return transactionRepository.findAll(accountId);
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Account not found."));
    }
}
