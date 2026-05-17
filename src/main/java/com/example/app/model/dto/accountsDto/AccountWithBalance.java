package com.example.app.model.dto.accountsDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.example.app.model.enums.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountWithBalance(
        @NotNull UUID id,

        @NotNull AccountType accountType,

        @NotBlank String currency,

        @NotNull Boolean active,

        @NotNull LocalDateTime createdAt,

        @NotNull LocalDateTime updatedAt,

        @NotNull Long user_id,
        
        @NotNull BigDecimal balance // calculated sum(credits) - sum (debits)
) {
}
