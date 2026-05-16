package com.example.app.service;

import com.example.app.model.dto.AccountBasic;
import com.example.app.model.dto.UserBasic;
import com.example.app.model.entity.Accounts;
import com.example.app.model.entity.Users;
import com.example.app.model.enums.AccountType;
import com.example.app.repository.AccountsRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class AccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public AccountBasic addAccount(Users user) {
        Accounts accountEntity = new Accounts();
        accountEntity.setUser(user);
        accountEntity.setAccountType(AccountType.WALLET);
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

    public AccountBasic getAccountById(UUID id) {

        Accounts account = accountsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        return new AccountBasic(
                account.getId(),
                account.getAccountType(),
                account.getCurrency(),
                account.getActive(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getUser().getId());
    }

    public Page<AccountBasic> getAllAccounts(Pageable pageable) {

        Page<Accounts> accountsPage = accountsRepository.findAll(pageable);

        return accountsPage.map(account -> new AccountBasic(
                account.getId(),
                account.getAccountType(),
                account.getCurrency(),
                account.getActive(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getUser().getId()));
    }

}