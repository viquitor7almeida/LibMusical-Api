package com.libmusical.api.controllers;

import com.libmusical.api.dto.MusicChords.MusicChordsRequestDTO;
import com.libmusical.api.dto.MusicChords.MusicChordsResponseDTO;
import com.libmusical.api.services.MusicChordsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/chords")
@RequiredArgsConstructor
public class MusicChordsController {

    private final MusicChordsService musicChordsService;

    @PostMapping
    public ResponseEntity<MusicChordsResponseDTO> create(@RequestBody @Valid MusicChordsRequestDTO dto) {
        return ResponseEntity.status(201).body(musicChordsService.create(dto));
    }

    @PatchMapping("/{id}/photo")
    public ResponseEntity<MusicChordsResponseDTO> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(musicChordsService.uploadPhoto(id, file));
    }

    @GetMapping("/music/{musicId}")
    public ResponseEntity<List<MusicChordsResponseDTO>> getByMusicId(@PathVariable Long musicId) {
        return ResponseEntity.ok(musicChordsService.findByMusicId(musicId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        musicChordsService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicChordsResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MusicChordsRequestDTO dto) {
        return ResponseEntity.ok(musicChordsService.update(id, dto));
    }
}