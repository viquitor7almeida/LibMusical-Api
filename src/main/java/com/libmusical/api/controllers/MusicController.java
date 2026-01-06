package com.libmusical.api.controllers;

import com.libmusical.api.dto.Music.MusicRequestDTO;
import com.libmusical.api.dto.Music.MusicResponseDTO;
import com.libmusical.api.services.MusicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/musics")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @PostMapping
    public ResponseEntity<MusicResponseDTO> create(@RequestBody @Valid MusicRequestDTO dto) {
        return ResponseEntity.status(201).body(musicService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<MusicResponseDTO>> getAll() {
        return ResponseEntity.ok(musicService.findAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<MusicResponseDTO>> getByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(musicService.findByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        musicService.delete(id);
        return ResponseEntity.noContent().build(); 
        }
    
    @PutMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MusicRequestDTO dto) {
        MusicResponseDTO response = musicService.update(id, dto);
        return ResponseEntity.ok(response);
    }
}