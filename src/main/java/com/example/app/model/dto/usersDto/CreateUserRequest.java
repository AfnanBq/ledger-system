package com.example.app.model.dto.usersDto;

import com.example.app.model.enums.AccountType;

import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @NotBlank(message = "Username is required") String name,

        @Email(message = "Invalid email format") String email,

        AccountType accountType

) {
    public CreateUserRequest{
        if (accountType == null){
            accountType = AccountType.CUSTOMER_WALLET;

        }    }
}
