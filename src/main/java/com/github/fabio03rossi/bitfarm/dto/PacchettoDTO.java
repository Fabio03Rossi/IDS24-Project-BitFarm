package com.github.fabio03rossi.bitfarm.dto;

import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;

import java.util.HashMap;

public record PacchettoDTO(
        HashMap<Prodotto, Integer> listaProdotti,
        String nome,
        String description,
        double price,
        int id,
        String Certificazioni
){}
