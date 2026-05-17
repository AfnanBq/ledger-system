package com.example.app.model.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.app.model.enums.EntryType;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ledger_entries")
public class LedgerEntries {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transactions transaction;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Accounts account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType entryType;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}