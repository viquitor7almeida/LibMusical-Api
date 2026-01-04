package com.libmusical.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserPasswordUpdateDTO(
    @NotBlank 
    @Size(min = 6) 
    String newPassword
) {}