package com.libmusical.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "verses")
@Data
public class VersesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String lyrics;

    @Column(columnDefinition = "TEXT")
    private String chords;

    private Integer position;

    @ManyToOne
    @JoinColumn(name = "music_id")
    private MusicModel music;

}