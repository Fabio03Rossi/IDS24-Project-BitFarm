package com.github.fabio03rossi.bitfarm.dto;

import com.github.fabio03rossi.bitfarm.acquisto.Carrello;

public record OrdineDTO(
         int id,
         String indirizzo,
         String metodoPagamento,
         Carrello carrello,
         int idUtente
){}


