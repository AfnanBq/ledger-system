package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.model.entity.Accounts;
import com.example.app.model.enums.AccountType;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    // crud to return account based on id
    Optional<Accounts> findById(UUID id);

    // crud to return account based on id and type 
     Optional<Accounts> findByIdAndAccountType(UUID id, AccountType accountType);

    // crud to return account based on account_type
    Optional<Accounts> findByAccountType(AccountType accountType); 

    // List all accounts
    List<Accounts> findAll();
}
