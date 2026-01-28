package com.libmusical.api.dto.Verses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VersesRequestDTO(
    @NotBlank(message = "A letra é obrigatória")
    String lyrics,

    String chords,

    @NotNull(message = "O ID da música é obrigatório")
    Long musicId
) {}