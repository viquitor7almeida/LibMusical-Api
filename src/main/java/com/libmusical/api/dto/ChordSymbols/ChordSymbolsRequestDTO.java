package com.libmusical.api.dto.ChordSymbols;

import jakarta.validation.constraints.NotBlank;

public record ChordSymbolsRequestDTO(
    @NotBlank String fullSheet
) {}