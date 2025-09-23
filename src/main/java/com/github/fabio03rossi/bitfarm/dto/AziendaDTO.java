package com.github.fabio03rossi.bitfarm.dto;

public record AziendaDTO(
        String partitaIVA,
        String nome,
        String email,
        String password,
        String descrizione,
        String indirizzo,
        String telefono,
        String tipologia,
        String certificazioni
)
{}
