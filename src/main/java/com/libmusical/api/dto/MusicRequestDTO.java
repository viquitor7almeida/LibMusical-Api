package com.libmusical.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MusicRequestDTO(
    @NotBlank(message = "O compositor é obrigatório")
    String composers,

    @NotNull(message = "O ID do usuário é obrigatório")
    Long userId
) {}