package com.libmusical.api.controllers;

import com.libmusical.api.dto.Verses.VersesRequestDTO;
import com.libmusical.api.dto.Verses.VersesResponseDTO;
import com.libmusical.api.services.VersesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/verses")
@RequiredArgsConstructor
public class VersesController {

    private final VersesService versesService;

    @PostMapping
    public ResponseEntity<VersesResponseDTO> create(@RequestBody @Valid VersesRequestDTO dto) {
        return ResponseEntity.status(201).body(versesService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<VersesResponseDTO>> getAll() {
        return ResponseEntity.ok(versesService.findAll());
    }

    @GetMapping("/{musicId}")
    public ResponseEntity<List<VersesResponseDTO>> getByMusicId(@PathVariable Long musicId) {
        return ResponseEntity.ok(versesService.findByMusicId(musicId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VersesResponseDTO> update(@PathVariable Long id, @Valid @RequestBody VersesRequestDTO dto) {
        return ResponseEntity.ok(versesService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        versesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}