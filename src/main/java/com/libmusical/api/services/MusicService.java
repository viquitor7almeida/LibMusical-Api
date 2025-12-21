package com.libmusical.api.services;

import com.libmusical.api.dto.MusicDTO;
import com.libmusical.api.models.MusicModel;
import com.libmusical.api.models.UserModel;  
import com.libmusical.api.repositories.MusicRepository;
import com.libmusical.api.repositories.UserRepository;

import jakarta.transaction.Transactional;
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
    public MusicModel createMusic(MusicDTO dto) { // Recebe o DTO completo
        UserModel user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
                
        MusicModel music = new MusicModel();
        music.setUser(user);
        music.setComposers(dto.composers()); // <--- Agora ele salva o que veio no JSON

    return musicRepository.save(music);
}
}