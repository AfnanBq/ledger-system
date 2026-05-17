package com.example.app.model.dto.ledgerEntriesDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.app.model.enums.EntryType;

public record LedgerEntryBasic(
        UUID id,
        UUID accountId,
        UUID transactionId,
        EntryType entryType,
        BigDecimal amount,
        LocalDateTime createdAt) {}
