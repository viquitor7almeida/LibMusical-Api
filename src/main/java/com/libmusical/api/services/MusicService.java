package com.libmusical.api.services;

import com.libmusical.api.dto.MusicRequestDTO;
import com.libmusical.api.dto.MusicResponseDTO;
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
@RequiredArgsConstructor // Cria o construtor automaticamente para os campos 'final'
public class MusicService {
    
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    @Transactional
    public MusicResponseDTO create(MusicRequestDTO dto) {
        //Busca o dono da música
        UserModel user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException(dto.userId()));
                
        //Cria a entidade e mapeia os dados
        MusicModel music = new MusicModel();
        copyDtoToEntity(dto, music, user);

        //Salva e converte para ResponseDTO
        return new MusicResponseDTO(musicRepository.save(music));
    }

    public List<MusicResponseDTO> findAll() {
        return musicRepository.findAll()
                .stream()
                .map(MusicResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void copyDtoToEntity(MusicRequestDTO dto, MusicModel entity, UserModel user) {
        entity.setComposers(dto.composers());
        entity.setUser(user);
    }
}