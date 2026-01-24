package com.libmusical.api.dto.Music;

import com.libmusical.api.models.MusicModel;

public record MusicResponseDTO(
    Long id,
    String composers,
    Long userId,
    String userName
) {
    public MusicResponseDTO(MusicModel music) {
        this(
            music.getId(), 
            music.getComposers(), 
            music.getUser().getId(), 
            music.getUser().getName()
        );
    }
}