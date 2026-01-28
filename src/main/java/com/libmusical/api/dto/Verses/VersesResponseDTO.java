package com.libmusical.api.dto.Verses;

import com.libmusical.api.models.VersesModel;

public record VersesResponseDTO(
    Long id,
    String lyrics,
    String chords,
    Long musicId
) {
    public VersesResponseDTO(VersesModel verse) {
        this(
            verse.getId(),
            verse.getLyrics(),
            verse.getChords(),
            verse.getMusic().getId()
        );
    }
}