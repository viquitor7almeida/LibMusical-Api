package com.libmusical.api.dto.MusicChords;

import jakarta.validation.constraints.NotNull;

public record MusicChordsRequestDTO (
    
    String chordName,

    String photoUrl,

    @NotNull
    Integer position,

    @NotNull(message = "O ID da música é obrigatório")
    Long musicId
){}
