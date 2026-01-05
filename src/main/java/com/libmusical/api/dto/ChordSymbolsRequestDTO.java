package com.libmusical.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChordSymbolsRequestDTO(
    @NotNull Long musicId,
    @NotBlank String fullSheet
) {}