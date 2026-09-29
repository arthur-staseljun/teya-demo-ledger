package com.teya.demo.ledger.controller;

import com.teya.demo.ledger.model.Account;
import com.teya.demo.ledger.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountId}")
    public Account createAccount(@PathVariable String accountId) {
        return accountService.createAccount(parse(accountId));
    }

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable String accountId) {
        return accountService.getAccount(parse(accountId));
    }

    private Long parse(String accountId) {
        try {
            return Long.parseLong(accountId);
        }  catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid account id: " + accountId);
        }
    }
}
