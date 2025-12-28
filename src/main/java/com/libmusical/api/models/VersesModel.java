package com.libmusical.api.models;

import jakarta.persistence.*;

@Entity
@Table(name = "verses")
public class VersesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categories;

    @Column(columnDefinition = "TEXT")
    private String lyrics;

    @Column(columnDefinition = "TEXT")
    private String chords;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private MusicModel music;

}