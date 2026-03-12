package com.libmusical.api.services;

import com.libmusical.api.dto.Music.MusicRequestDTO;
import com.libmusical.api.dto.Music.MusicResponseDTO;
import com.libmusical.api.models.MusicModel;
import com.libmusical.api.models.UserModel;
import com.libmusical.api.repositories.MusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value; 
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.libmusical.api.enums.MusicType;

@Service
@RequiredArgsConstructor
public class MusicService {
    
    private final MusicRepository musicRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Transactional
    public MusicResponseDTO create(MusicRequestDTO dto) {
        UserModel user = getAuthenticatedUser();
        MusicModel music = new MusicModel();
        copyDtoToEntity(dto, music, user);
        return new MusicResponseDTO(musicRepository.save(music));
    }

    @Transactional
    public MusicResponseDTO uploadAudio(Long id, MultipartFile file) {
        MusicModel music = musicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        UserModel userLogado = getAuthenticatedUser();
        if (!music.getUser().getId().equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão.");
        }

        try {
            Path rootLocation = Paths.get(uploadDir);
            if (!Files.exists(rootLocation)) {
                Files.createDirectories(rootLocation);
            }

            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path destinationFile = rootLocation.resolve(filename);

            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

            music.setAudioUrl(filename);
            return new MusicResponseDTO(musicRepository.save(music));

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar arquivo");
        }
    }

    public List<MusicResponseDTO> findAll() {
        return musicRepository.findAll().stream()
                .map(MusicResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<MusicResponseDTO> findByUserIdAndType(Long userId, MusicType type){
        return musicRepository.findByUserIdAndType(userId, type).stream()

            .map(MusicResponseDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public MusicResponseDTO update(Long id, MusicRequestDTO dto) {
        MusicModel music = musicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        UserModel userLogado = getAuthenticatedUser();
        if (!music.getUser().getId().equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão.");
        }

        copyDtoToEntity(dto, music, music.getUser());
        return new MusicResponseDTO(musicRepository.save(music));
    }

    @Transactional
    public void delete(Long id) {
        MusicModel music = musicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        UserModel userLogado = getAuthenticatedUser();
        if (!music.getUser().getId().equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão.");
        }

        try {
            if (music.getAudioUrl() != null) {
                Files.deleteIfExists(Paths.get(uploadDir).resolve(music.getAudioUrl()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao remover arquivo físico: " + e.getMessage());
        }

        musicRepository.delete(music);
    }

    private UserModel getAuthenticatedUser() {
        return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void copyDtoToEntity(MusicRequestDTO dto, MusicModel entity, UserModel user) {
        entity.setName(dto.name());
        entity.setComposers(dto.composers());
        entity.setUser(user);
        entity.setType(dto.type());
    }
}