package com.example.app.service;

import com.example.app.model.dto.accountsDto.AccountWithBalance;
import com.example.app.model.dto.accountsDto.AccountBasic;
import com.example.app.model.dto.ledgerEntriesDto.LedgerEntryBasic;
import com.example.app.model.entity.Accounts;
import com.example.app.model.entity.Users;
import com.example.app.model.enums.AccountType;
import com.example.app.repository.AccountsRepository;
import com.example.app.repository.LedgerEntriesRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.math.BigDecimal;

@Service
public class AccountsService {

    private final AccountsRepository accountsRepository;
    private final LedgerEntriesRepository ledgerEntriesRepository;

    public AccountsService(AccountsRepository accountsRepository,
            LedgerEntriesRepository ledgerEntriesRepository) {
        this.accountsRepository = accountsRepository;
        this.ledgerEntriesRepository = ledgerEntriesRepository;
    }

    public AccountBasic addAccount(Users user, AccountType accountType) {
        // create new account
        Accounts accountEntity = new Accounts();
        accountEntity.setUser(user);
        accountEntity.setAccountType(accountType);
        Accounts accountRecord = accountsRepository.save(accountEntity);

        return new AccountBasic(
                accountRecord.getId(),
                accountRecord.getAccountType(),
                accountRecord.getCurrency(),
                accountRecord.getActive(),
                accountRecord.getCreatedAt(),
                accountRecord.getUpdatedAt(),
                accountRecord.getUser().getId());
    }

    public AccountWithBalance getAccountById(UUID id) {

        Accounts account = accountsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        // derived account balance from ledger table
        BigDecimal accountBalance = ledgerEntriesRepository.calculateAccountBalance(account.getId());

        return new AccountWithBalance(
                account.getId(),
                account.getAccountType(),
                account.getCurrency(),
                account.getActive(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getUser().getId(),
                accountBalance);
    }

    public Page<AccountWithBalance> getAllAccounts(Pageable pageable) {

        Page<Accounts> accountsPage = accountsRepository.findAll(pageable);

        return accountsPage.map(account -> {

            BigDecimal accountBalance = ledgerEntriesRepository.calculateAccountBalance(account.getId());

            return new AccountWithBalance(
                    account.getId(),
                    account.getAccountType(),
                    account.getCurrency(),
                    account.getActive(),
                    account.getCreatedAt(),
                    account.getUpdatedAt(),
                    account.getUser().getId(),
                    accountBalance);
        });
    }

    public Page<LedgerEntryBasic> getAccountLedger(UUID accountId, Pageable pageable) {
        // get all account's ledger entries
        return ledgerEntriesRepository.findByAccountId(accountId, pageable);

    }
}