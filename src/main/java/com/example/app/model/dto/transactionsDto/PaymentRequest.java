package com.example.app.model.dto.transactionsDto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PaymentRequest(

    @NotNull(message = "From account is required")
    UUID fromAccountId,

    @NotNull(message = "To account is required")
    UUID toAccountId,

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    BigDecimal amount,

    @NotNull(message = "Fee is required")
    @PositiveOrZero(message = "Fee cannot be negative")
    BigDecimal fee,

    @NotNull(message = "RequestId is required")
    UUID requestId
) {}