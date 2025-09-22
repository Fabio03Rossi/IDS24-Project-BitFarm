package com.github.fabio03rossi.bitfarm.dto;

import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;

import java.util.HashMap;

public record PacchettoDTO(
        //HashMap<Integer, Integer> prodotti,
        String nome,
        String descrizione,
        double prezzo,
        String certificazioni
){}
