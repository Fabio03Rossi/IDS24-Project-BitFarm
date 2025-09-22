package com.github.fabio03rossi.bitfarm.dto;

public record ProdottoDTO(
        int id,
        String name,
        String description,
        Double price,
        String certs
){}

