package com.libmusical.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "chord_symbols")
public class ChordSymbolsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private MusicModel music;

    // "TEXT" garante que o SQLite não limite o tamanho e preserve a formatação
    @Column(columnDefinition = "TEXT")
    private String fullSheet;

    // Getters e Setters
}