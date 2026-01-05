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

    @Column(columnDefinition = "TEXT")
    private String fullSheet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MusicModel getMusic() {
        return music;
    }

    public void setMusic(MusicModel music) {
        this.music = music;
    }

    public String getFullSheet() {
        return fullSheet;
    }

    public void setFullSheet(String fullSheet) {
        this.fullSheet = fullSheet;
    }
}