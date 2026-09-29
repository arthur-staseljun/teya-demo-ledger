package com.teya.demo.ledger.service;

import com.teya.demo.ledger.exception.classification.ErrorClassification;
import com.teya.demo.ledger.exception.classification.LedgerServiceException;
import com.teya.demo.ledger.model.Account;
import com.teya.demo.ledger.persistence.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Account createAccount() {
        return accountRepository.save(new Account());
    }

    public Account getExistingAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new LedgerServiceException(ErrorClassification.ACCOUNT_DOES_NOT_EXIST));
    }
}
