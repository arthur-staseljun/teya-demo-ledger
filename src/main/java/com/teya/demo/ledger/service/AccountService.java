package com.teya.demo.ledger.service;

import com.teya.demo.ledger.model.Account;
import com.teya.demo.ledger.persistance.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount(Long accountId) {
        if (accountRepository.findById(accountId).isPresent()) {
            throw new IllegalArgumentException("Account already exists!");
        }
        return accountRepository.save(new Account(accountId));
    }

    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account does not exist"));
    }
}
