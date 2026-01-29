package com.libmusical.api.services;

import com.libmusical.api.dto.Music.MusicRequestDTO;
import com.libmusical.api.dto.Music.MusicResponseDTO;
import com.libmusical.api.models.MusicModel;
import com.libmusical.api.models.UserModel;
import com.libmusical.api.repositories.MusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

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

@Service
@RequiredArgsConstructor
public class MusicService {
    
    private final MusicRepository musicRepository;
    private final Path rootLocation = Paths.get("uploads");

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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para alterar esta música.");
        }

        try {
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
        return musicRepository.findAll()
                .stream()
                .map(MusicResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<MusicResponseDTO> findByUserId(Long userId){
        return musicRepository.findByUserId(userId)
            .stream()
            .map(MusicResponseDTO::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public MusicResponseDTO update(Long id, MusicRequestDTO dto) {
        MusicModel music = musicRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        UserModel userLogado = getAuthenticatedUser();

        if (!music.getUser().getId().equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para alterar esta música.");
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
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar esta música.");
        }

        musicRepository.delete(music);
    }

    private UserModel getAuthenticatedUser() {
        return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void copyDtoToEntity(MusicRequestDTO dto, MusicModel entity, UserModel user) {
        entity.setName(dto.name());
        entity.setComposers(dto.composers());
        entity.setAudioUrl(dto.audioUrl());
        entity.setUser(user);
    }
}