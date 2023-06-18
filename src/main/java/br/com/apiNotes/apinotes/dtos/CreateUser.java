package br.com.apiNotes.apinotes.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUser(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password,
        @NotBlank
        String repassword
)
{}
