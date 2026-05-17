package com.example.app.model.dto.usersDto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.app.model.dto.accountsDto.AccountBasic;

import jakarta.validation.constraints.*;

public record UserBasic(
        @NotNull Long id,

        @NotBlank String name,

        @Email @NotBlank String email,

        @NotNull LocalDateTime createdAt,

        @NotEmpty List<AccountBasic> accounts

) {
}
