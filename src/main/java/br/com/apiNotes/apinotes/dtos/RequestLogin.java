package br.com.apiNotes.apinotes.dtos;

import jakarta.validation.constraints.NotBlank;

public record RequestLogin(@NotBlank String email, @NotBlank String password) {
}
