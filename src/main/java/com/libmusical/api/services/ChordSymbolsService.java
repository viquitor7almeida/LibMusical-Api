package com.libmusical.api.services;

import com.libmusical.api.dto.ChordSymbols.ChordSymbolsRequestDTO;
import com.libmusical.api.dto.ChordSymbols.ChordSymbolsResponseDTO;
import com.libmusical.api.models.ChordSymbolsModel;
import com.libmusical.api.models.MusicModel;
import com.libmusical.api.repositories.ChordSymbolsRepository;
import com.libmusical.api.repositories.MusicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChordSymbolsService {

    private final ChordSymbolsRepository chordSymbolsRepository;
    private final MusicRepository musicRepository;

    public ChordSymbolsService(ChordSymbolsRepository chordSymbolsRepository, MusicRepository musicRepository) {
        this.chordSymbolsRepository = chordSymbolsRepository;
        this.musicRepository = musicRepository;
    }

    @Transactional
    public ChordSymbolsResponseDTO create(ChordSymbolsRequestDTO dto) {
        MusicModel music = musicRepository.findById(dto.musicId())
                .orElseThrow(() -> new RuntimeException("Music not found with id: " + dto.musicId()));

        ChordSymbolsModel chordSymbols = new ChordSymbolsModel();
        chordSymbols.setMusic(music);
        chordSymbols.setFullSheet(dto.fullSheet());

        return new ChordSymbolsResponseDTO(chordSymbolsRepository.save(chordSymbols));
    }

    public List<ChordSymbolsResponseDTO> findAll() {
        return chordSymbolsRepository.findAll()
                .stream()
                .map(ChordSymbolsResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ChordSymbolsResponseDTO> findByMusicId(Long musicId) {
        return chordSymbolsRepository.findByMusicId(musicId)
                .stream()
                .map(ChordSymbolsResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ChordSymbolsResponseDTO update(Long id, ChordSymbolsRequestDTO dto) {
        ChordSymbolsModel chordSymbols = chordSymbolsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chord symbol not found with id: " + id));

        MusicModel music = musicRepository.findById(dto.musicId())
                .orElseThrow(() -> new RuntimeException("Music not found with id: " + dto.musicId()));

        chordSymbols.setMusic(music);
        chordSymbols.setFullSheet(dto.fullSheet());

        return new ChordSymbolsResponseDTO(chordSymbolsRepository.save(chordSymbols));
    }

    @Transactional
    public void delete(Long id) {
        if (!chordSymbolsRepository.existsById(id)) {
            throw new RuntimeException("Chord symbol not found id: " + id);
        }
        chordSymbolsRepository.deleteById(id);
    }
}