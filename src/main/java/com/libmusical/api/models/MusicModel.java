package com.libmusical.api.models;

import jakarta.persistence.*;
import lombok.Data; 
import java.util.List;

import com.libmusical.api.enums.MusicType;

@Entity
@Table(name = "musics")
@Data
public class MusicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String composers;
    
    private String name;

    private String audioUrl;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable=false )
    private MusicType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user; 

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VersesModel> verses;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChordSymbolsModel> chords;

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MusicChordsModel> musicChords;
}