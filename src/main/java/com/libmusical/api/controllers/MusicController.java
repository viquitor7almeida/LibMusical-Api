package com.libmusical.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libmusical.api.dto.MusicDTO;
import com.libmusical.api.models.MusicModel;
import com.libmusical.api.services.MusicService;

@RestController
@RequestMapping("/music")
public class MusicController {

    private final MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }
    
    @PostMapping
    public ResponseEntity<MusicModel> create(@RequestBody MusicDTO dto) {
        MusicModel savedMusic = musicService.createMusic(dto); 
        return ResponseEntity.status(201).body(savedMusic);
    }
}