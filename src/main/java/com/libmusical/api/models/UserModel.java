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
    
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<MusicModel> musics;
}