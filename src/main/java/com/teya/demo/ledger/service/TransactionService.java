package com.teya.demo.ledger.service;

import com.teya.demo.ledger.exception.classification.ErrorClassification;
import com.teya.demo.ledger.exception.classification.LedgerServiceException;
import com.teya.demo.ledger.model.Account;
import com.teya.demo.ledger.model.Currency;
import com.teya.demo.ledger.model.Transaction;
import com.teya.demo.ledger.persistence.AccountRepository;
import com.teya.demo.ledger.persistence.TransactionRepository;
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
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new LedgerServiceException(ErrorClassification.AMOUNT_PARSE_ERROR);
        }
        Account account = getAccount(accountId);
        if (!account.getCurrency().equals(currency)) {
            throw new LedgerServiceException(ErrorClassification.CURRENCY_MISMATCH);
        }
        BigDecimal newBalance = accountRepository.applyBalanceChange(accountId, amount)
                .orElseThrow(() -> new LedgerServiceException(ErrorClassification.INSUFFICIENT_FUNDS));
        return transactionRepository.save(new Transaction(accountId, amount, newBalance));
    }

    public List<Transaction> getAllTransactions(Long accountId) {
        getAccount(accountId);
        return transactionRepository.findAll(accountId);
    }

    private Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new LedgerServiceException(ErrorClassification.ACCOUNT_DOES_NOT_EXIST));
    }
}
