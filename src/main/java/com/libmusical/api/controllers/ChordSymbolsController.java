package com.libmusical.api.controllers;

import com.libmusical.api.dto.ChordSymbols.ChordSymbolsRequestDTO;
import com.libmusical.api.dto.ChordSymbols.ChordSymbolsResponseDTO;
import com.libmusical.api.services.ChordSymbolsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/chordsymbols")
@RequiredArgsConstructor
public class ChordSymbolsController {

    private final ChordSymbolsService chordSymbolsService;

    @PostMapping
    public ResponseEntity<ChordSymbolsResponseDTO> create(@RequestBody @Valid ChordSymbolsRequestDTO dto) {
        return ResponseEntity.status(201).body(chordSymbolsService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ChordSymbolsResponseDTO>> getAll() {
        return ResponseEntity.ok(chordSymbolsService.findAll());
    }

    @GetMapping("/music/{id}")
    public ResponseEntity<List<ChordSymbolsResponseDTO>> getByUserId(@PathVariable Long musicId){
        return ResponseEntity.ok(chordSymbolsService.findByMusicId(musicId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chordSymbolsService.delete(id);
        return ResponseEntity.noContent().build(); 
        }
    
    @PutMapping("/{id}")
    public ResponseEntity<ChordSymbolsResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ChordSymbolsRequestDTO dto) {
        ChordSymbolsResponseDTO response = chordSymbolsService.update(id, dto);
        return ResponseEntity.ok(response);
    }
}