package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.model.entity.Accounts;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    // crud to return account based on id
    Optional<Accounts> findById(UUID id);

    // List all accounts
    List<Accounts> findAll();
}
