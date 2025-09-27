package com.github.fabio03rossi.bitfarm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record EventoDTO (
        @NotBlank String nome,
        @NotBlank String descrizione,
        @NotNull Date data,
        @NotBlank String posizione
) {}