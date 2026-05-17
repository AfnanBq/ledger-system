package com.example.app.model.dto.transactionsDto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record SettlementRequest(

        @NotNull(message = "Merchant account is required") UUID merchantAccountId,

        @NotNull(message = "Transaction id is required") UUID transactionId,

        @NotNull(message = "RequestId is required") UUID requestId) {
}