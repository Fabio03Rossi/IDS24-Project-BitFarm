package com.github.fabio03rossi.bitfarm.dto;

import java.util.Date;

public record EventoDTO(
        int id,
        String nome,
        String descrizione,
        Date data,
        int numeroPartecipanti,
        String posizione
        ){}
