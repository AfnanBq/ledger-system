package com.example.app.model.dto.transactionsDto;

import com.example.app.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.app.model.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public record TransactionBasic(
        @NotNull UUID id,

        @NotNull TransactionType type,

        @NotNull TransactionStatus status,

        @NotNull UUID requestId,

        @NotNull BigDecimal amount,

        @NotNull LocalDateTime createdAt) {
}
