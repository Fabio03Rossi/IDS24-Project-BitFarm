package com.github.fabio03rossi.bitfarm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UtenteDTO (
        @NotBlank String nickname,
        @Email String email,
        @NotBlank String password,
        @NotBlank String indirizzo
) {}
