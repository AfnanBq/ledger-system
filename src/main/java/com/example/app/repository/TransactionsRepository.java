package com.example.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.model.dto.transactionsDto.TransactionBasic;
import com.example.app.model.entity.Transactions;

import java.util.Optional;
import java.util.UUID;
// import java.util.List;

public interface TransactionsRepository extends JpaRepository<Transactions, Long>{

    // crud to return account based on id
    Optional<TransactionBasic> findById(UUID id);

    // crud to return account based on request_id
    Optional<TransactionBasic> findByRequestId(UUID requestId);
}
