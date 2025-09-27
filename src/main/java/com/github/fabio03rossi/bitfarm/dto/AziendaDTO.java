package com.github.fabio03rossi.bitfarm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AziendaDTO (
        @NotBlank String partitaIVA,
        @NotBlank String nome,
        @Email String email,
        @NotBlank String password,
        @NotBlank String descrizione,
        @NotBlank String indirizzo,
        @NotBlank String telefono,
        @NotBlank String tipologia,
        @NotBlank String certificazioni
) {}
