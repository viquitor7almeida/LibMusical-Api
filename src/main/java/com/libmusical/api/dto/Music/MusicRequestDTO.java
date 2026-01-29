package com.libmusical.api.dto.Music;

import jakarta.validation.constraints.NotBlank;

public record MusicRequestDTO(
    @NotBlank(message = "O compositor é obrigatório")
    String composers,

    @NotBlank(message = "O nome é obrigatório")
    String name,
    
    String audioUrl
) {}