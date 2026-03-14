package com.libmusical.api.services;

import com.libmusical.api.dto.MusicChords.MusicChordsRequestDTO;
import com.libmusical.api.dto.MusicChords.MusicChordsResponseDTO;
import com.libmusical.api.models.MusicChordsModel;
import com.libmusical.api.models.MusicModel;
import com.libmusical.api.models.UserModel;
import com.libmusical.api.repositories.MusicChordsRepository;
import com.libmusical.api.repositories.MusicRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

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
public class MusicChordsService {

    private final MusicChordsRepository musicChordsRepository;
    private final MusicRepository musicRepository;

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Transactional
    public MusicChordsResponseDTO create(MusicChordsRequestDTO dto) {
        MusicModel music = musicRepository.findById(dto.musicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        UserModel userLogado = getAuthenticatedUser();
        if (!music.getUser().getId().equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão.");
        }

        MusicChordsModel chord = new MusicChordsModel();
        copyDtoToEntity(dto, chord, music);
        return new MusicChordsResponseDTO(musicChordsRepository.save(chord));
    }

    @Transactional
    public MusicChordsResponseDTO uploadPhoto(Long id, MultipartFile file) {
        MusicChordsModel chord = musicChordsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Acorde não encontrado"));

        UserModel userLogado = getAuthenticatedUser();
        if (!chord.getMusic().getUser().getId().equals(userLogado.getId())) {
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

            if (chord.getPhotoUrl() != null) {
                Files.deleteIfExists(rootLocation.resolve(chord.getPhotoUrl()));
            }

            chord.setPhotoUrl(filename);
            return new MusicChordsResponseDTO(musicChordsRepository.save(chord));

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar imagem");
        }
    }

    public List<MusicChordsResponseDTO> findByMusicId(Long musicId) {
        return musicChordsRepository.findByMusicId(musicId).stream()
                .map(MusicChordsResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        MusicChordsModel chord = musicChordsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Acorde não encontrado"));

        UserModel userLogado = getAuthenticatedUser();
        if (!chord.getMusic().getUser().getId().equals(userLogado.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão.");
        }

        try {
            if (chord.getPhotoUrl() != null) {
                Files.deleteIfExists(Paths.get(uploadDir).resolve(chord.getPhotoUrl()));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        musicChordsRepository.delete(chord);
    }

    @Transactional
public MusicChordsResponseDTO update(Long id, MusicChordsRequestDTO dto) {
    MusicChordsModel chord = musicChordsRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Acorde não encontrado"));

    UserModel userLogado = getAuthenticatedUser();
    if (!chord.getMusic().getUser().getId().equals(userLogado.getId())) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Sem permissão.");
    }

    copyDtoToEntity(dto, chord, chord.getMusic());
    return new MusicChordsResponseDTO(musicChordsRepository.save(chord));
}

    private UserModel getAuthenticatedUser() {
        return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void copyDtoToEntity(MusicChordsRequestDTO dto, MusicChordsModel entity, MusicModel music) {
        entity.setChordName(dto.chordName());
        entity.setPosition(dto.position());
        entity.setMusic(music);
    }
}