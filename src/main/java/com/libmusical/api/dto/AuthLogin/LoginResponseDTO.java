package com.libmusical.api.dto.AuthLogin;

public record LoginResponseDTO(String token, Long id, String name, String email) {}