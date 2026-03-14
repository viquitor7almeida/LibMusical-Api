package com.libmusical.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chord_symbols")
@Data
public class ChordSymbolsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private MusicModel music;

    @Column(columnDefinition = "TEXT")
    private String fullSheet;
}