package com.github.fabio03rossi.bitfarm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

public record UtenteDTO (
        @Null Integer id,
        @NotBlank String nickname,
        @Email String email,
        @NotBlank String password,
        @NotBlank String indirizzo
) {}
