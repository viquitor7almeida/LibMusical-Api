package com.libmusical.api.dto.Music;

import com.libmusical.api.models.MusicModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public record MusicResponseDTO(
    Long id,
    String composers,
    String name,
    Long userId,
    String userName,
    String audioUrl
) {
    public MusicResponseDTO(MusicModel music) {
        this(
            music.getId(), 
            music.getComposers(), 
            music.getName(),
            music.getUser().getId(), 
            music.getUser().getName(),
            music.getAudioUrl() != null 
                ? ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(music.getAudioUrl())
                    .toUriString() 
                : null
        );
    }
}