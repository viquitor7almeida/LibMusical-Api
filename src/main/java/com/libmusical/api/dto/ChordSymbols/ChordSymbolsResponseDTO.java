package com.libmusical.api.dto.ChordSymbols;

import com.libmusical.api.models.ChordSymbolsModel;

public record ChordSymbolsResponseDTO(
    Long id,
    Long musicId,
    String fullSheet
) {
    public ChordSymbolsResponseDTO(ChordSymbolsModel chordSymbols) {
        this(
            chordSymbols.getId(),
            chordSymbols.getMusic() != null ? chordSymbols.getMusic().getId() : null,
            chordSymbols.getFullSheet()
        );
    }
}