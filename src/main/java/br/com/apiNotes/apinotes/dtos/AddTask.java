package br.com.apiNotes.apinotes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddTask(
        @NotBlank
        @NotNull
        String title,
        String description

) {
}
