package com.libmusical.api.controllers;

import com.libmusical.api.dto.AuthenticationDTO;
import com.libmusical.api.dto.LoginResponseDTO;
import com.libmusical.api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var response = authService.login(data);
        return ResponseEntity.ok(response);
    }
}