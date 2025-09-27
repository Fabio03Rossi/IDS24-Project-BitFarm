package com.github.fabio03rossi.bitfarm.dto;

import com.github.fabio03rossi.bitfarm.acquisto.Carrello;

public record OrdineDTO(
         String indirizzo,
         String metodoPagamento,
         Carrello carrello,
         int idUtente
) {}


