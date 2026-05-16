package com.example.app.model.dto;

import jakarta.validation.constraints.*;

public record CreateUserRequest(
        @NotBlank(message = "Username is required") String name,

        @Email(message = "Invalid email format") String email

) {
}
