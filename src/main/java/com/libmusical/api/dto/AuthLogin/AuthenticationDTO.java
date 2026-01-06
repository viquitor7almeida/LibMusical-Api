package com.libmusical.api.dto.AuthLogin;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(@NotBlank String email, @NotBlank String password) {}