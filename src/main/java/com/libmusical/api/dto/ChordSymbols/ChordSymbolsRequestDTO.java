package com.libmusical.api.dto.ChordSymbols;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChordSymbolsRequestDTO(
    @NotNull Long musicId,
    @NotBlank String fullSheet
) {}