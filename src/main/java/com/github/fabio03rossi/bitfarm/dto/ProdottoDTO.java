package com.github.fabio03rossi.bitfarm.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record ProdottoDTO (
        @NotBlank String nome,
        @NotBlank String descrizione,
        @NotNull double prezzo,
        @NotBlank String certificazioni
) {}

