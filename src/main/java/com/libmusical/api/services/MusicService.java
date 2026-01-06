package com.libmusical.api.services;

import com.libmusical.api.dto.Music.MusicRequestDTO;
import com.libmusical.api.dto.Music.MusicResponseDTO;
import com.libmusical.api.models.MusicModel;
import com.libmusical.api.models.UserModel;
import com.libmusical.api.repositories.MusicRepository;
import com.libmusical.api.repositories.UserRepository;
import com.libmusical.api.exceptions.UserNotFoundException; // Sua exceção personalizada
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicService {
    
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    @Transactional
    public MusicResponseDTO create(MusicRequestDTO dto) {
        UserModel user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException(dto.userId()));
                
        MusicModel music = new MusicModel();
        copyDtoToEntity(dto, music, user);

        return new MusicResponseDTO(musicRepository.save(music));
    }

    public List<MusicResponseDTO> findAll() {
        return musicRepository.findAll()
                .stream()
                .map(MusicResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<MusicResponseDTO> findByUserId (Long userId){
        return musicRepository.findByUserId(userId)
            .stream()
            .map(MusicResponseDTO::new)
            .collect(Collectors.toList());
    }

        @Transactional
    public MusicResponseDTO update(Long id, MusicRequestDTO dto) {
        MusicModel music = musicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Music not found with id: " + id));

        UserModel user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException(dto.userId()));

        copyDtoToEntity(dto, music, user);

        return new MusicResponseDTO(musicRepository.save(music));
    }

    @Transactional
    public void delete(Long id){
        if (!musicRepository.existsById(id)){
            throw new RuntimeException("Music not found id:" + id);
        }
        musicRepository.deleteById(id);
            
    }

    private void copyDtoToEntity(MusicRequestDTO dto, MusicModel entity, UserModel user) {
        entity.setComposers(dto.composers());
        entity.setUser(user);
    }


}