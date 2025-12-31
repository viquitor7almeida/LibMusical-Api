package com.libmusical.api.controllers;

import com.libmusical.api.dto.MusicRequestDTO;
import com.libmusical.api.dto.MusicResponseDTO;
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

    @GetMapping("/id")
    public ResponseEntity<List<MusicResponseDTO>> getByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(musicService.findByUserId(userId));
    }
}