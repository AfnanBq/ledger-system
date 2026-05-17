package com.example.app.model.dto.transactionsDto;

import java.math.BigDecimal;
import java.util.UUID;

public record DepositRequest (
    UUID accountId,
    BigDecimal amount,
    UUID requestId
){

}
    
