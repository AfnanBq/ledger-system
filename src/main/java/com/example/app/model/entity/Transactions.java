package com.example.app.model.entity;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.example.app.model.enums.TransactionStatus;
import com.example.app.model.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID requestId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Accounts account;

    @OneToMany(mappedBy = "transaction")
    private List<LedgerEntries> entries;
}