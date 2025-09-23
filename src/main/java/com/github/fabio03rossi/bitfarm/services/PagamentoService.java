package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.acquisto.Carrello;

public class PagamentoService implements IPagamentoService {

    @Override
    public boolean buy(Carrello carrello) {
        return true;
    }

    @Override
    public String getNome() {
        return "Standard";
    }
}
