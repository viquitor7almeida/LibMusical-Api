package com.libmusical.api.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO (

    String name,
    
    @Email 
    String email,
    
    @Size(min = 2) 
    String password
) {}
