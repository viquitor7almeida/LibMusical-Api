package com.libmusical.api.services;

import com.libmusical.api.dto.Verses.VersesRequestDTO;
import com.libmusical.api.dto.Verses.VersesResponseDTO;
import com.libmusical.api.models.MusicModel;
import com.libmusical.api.models.VersesModel;
import com.libmusical.api.repositories.MusicRepository;
import com.libmusical.api.repositories.VersesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VersesService {

    private final VersesRepository versesRepository;
    private final MusicRepository musicRepository;

    @Transactional
    public VersesResponseDTO create(VersesRequestDTO dto) {
        // Buscamos a música para garantir que ela existe antes de associar ao verso
        MusicModel music = musicRepository.findById(dto.musicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        VersesModel verse = new VersesModel();
        copyDtoToEntity(dto, verse, music);

        return new VersesResponseDTO(versesRepository.save(verse));
    }

    public List<VersesResponseDTO> findAll() {
        return versesRepository.findAll()
                .stream()
                .map(VersesResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<VersesResponseDTO> findByMusicId(Long musicId) {
        return versesRepository.findByMusicId(musicId)
                .stream()
                .map(VersesResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public VersesResponseDTO update(Long id, VersesRequestDTO dto) {
        VersesModel verse = versesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Verso não encontrado"));

        // Se o DTO permitir mudar a música do verso, buscamos a nova música
        MusicModel music = musicRepository.findById(dto.musicId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada"));

        copyDtoToEntity(dto, verse, music);
        return new VersesResponseDTO(versesRepository.save(verse));
    }

    @Transactional
    public void delete(Long id) {
        VersesModel verse = versesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Verso não encontrado"));

        versesRepository.delete(verse);
    }

    private void copyDtoToEntity(VersesRequestDTO dto, VersesModel entity, MusicModel music) {
        entity.setLyrics(dto.lyrics());
        entity.setChords(dto.chords());
        entity.setMusic(music);
    }
}