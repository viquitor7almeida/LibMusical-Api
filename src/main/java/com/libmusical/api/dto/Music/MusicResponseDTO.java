package com.libmusical.api.dto.Music;

import com.libmusical.api.models.MusicModel;

// O que vai para o Front-end
public record MusicResponseDTO(
    Long id,
    String composers,
    Long userId,
    String userName
) {
    // Construtor para converter a Entity para DTO facilmente
    public MusicResponseDTO(MusicModel music) {
        this(
            music.getId(), 
            music.getComposers(), 
            music.getUser().getId(), 
            music.getUser().getName()
        );
    }
}