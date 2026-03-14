package com.libmusical.api.dto.MusicChords;

import jakarta.validation.constraints.NotNull;

import com.libmusical.api.models.MusicChordsModel;

public record MusicChordsResponseDTO (
    Long id,

    String photoUrl,

    String chordName,

    @NotNull
    Integer position,

    @NotNull(message = "O ID da música é obrigatório")
    Long musicId

    
)   {

public MusicChordsResponseDTO (MusicChordsModel musicChords){
    this(
        musicChords.getId(),
        musicChords.getPhotoUrl(),
        musicChords.getChordName(),
        musicChords.getPosition(),
        musicChords.getMusic().getId()

        );
    }
}