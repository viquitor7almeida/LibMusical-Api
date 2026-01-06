package com.libmusical.api.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
    @NotBlank 
    String name,
    
    @NotBlank 
    @Email 
    String email,
    
    @NotBlank 
    @Size(min = 2) 
    String password
) {}