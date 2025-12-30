package com.libmusical.api.models;

import jakarta.persistence.*;
import lombok.Data; 
import java.util.List;

@Entity
@Table(name = "users")
@Data 
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<MusicModel> musics;
}