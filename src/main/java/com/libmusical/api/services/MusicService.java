package com.libmusical.api.services;

import com.libmusical.api.models.MusicModel;
import com.libmusical.api.models.UserModel;  
import com.libmusical.api.repositories.MusicRepository;
import com.libmusical.api.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class MusicService {
    
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    public MusicService(MusicRepository musicRepository, UserRepository userRepository) {
        this.musicRepository = musicRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public MusicModel createMusic(@NonNull Long userId) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        MusicModel music = new MusicModel();
        music.setUser(user);

        return musicRepository.save(music);
    }
}