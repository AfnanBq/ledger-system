package com.example.app.controller;
import com.example.app.model.dto.accountsDto.AccountWithBalance;
import com.example.app.model.dto.ledgerEntriesDto.LedgerEntryBasic;
import com.example.app.service.AccountsService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    @Autowired
    private AccountsService accountsService;

    @GetMapping("/{id}")
    public AccountWithBalance findUserById(@PathVariable UUID id) {
        return accountsService.getAccountById(id);
    }

    @GetMapping("/")
    public Page<AccountWithBalance> getAllUsers(Pageable pageable) {
        return accountsService.getAllAccounts(pageable);
    }

    @GetMapping("/{id}/ledger")
    public Page<LedgerEntryBasic> getAccountLedger(@PathVariable UUID id, Pageable pageable) {
        return accountsService.getAccountLedger(id, pageable);
    }
}
