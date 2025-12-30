package com.libmusical.api.dto;

import com.libmusical.api.models.UserModel;


public record UserResponseDTO(Long id, String name, String email) {
    public UserResponseDTO(UserModel user) {
        this(user.getId(), 
        user.getName(), 
        user.getEmail());
    }
}

    

