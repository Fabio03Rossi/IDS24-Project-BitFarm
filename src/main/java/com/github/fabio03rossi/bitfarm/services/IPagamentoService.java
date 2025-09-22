package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.acquisto.Carrello;

public interface IPagamentoService {
    public default boolean buy(Carrello carrello){return true;}
    public String getNome();
}
