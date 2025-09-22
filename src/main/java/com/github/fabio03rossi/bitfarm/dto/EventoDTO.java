package com.github.fabio03rossi.bitfarm.dto;

import java.util.Date;

public record EventoDTO(
        String nome,
        String descrizione,
        Date data,
        String posizione
)
{}
