package com.libmusical.api.models;

import jakarta.persistence.*;
import lombok.Data; 
import java.util.List;

@Entity
@Table(name = "musics")
@Data // Esta anotação cria automaticamente o setUser, getUser
public class MusicModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String composers;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user; 

    @OneToMany(mappedBy = "music")
    private List<VersesModel> verses;

    @OneToMany(mappedBy = "music")
    private List<ChordSymbolsModel> chords;
}