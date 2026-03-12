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

    @OneToMany(mappedBy = "music")
    private List<VersesModel> verses;

    @OneToMany(mappedBy = "music")
    private List<ChordSymbolsModel> chords;
}