package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;

import java.util.Map;

public interface IAcquistoService {

    Map<IArticolo, Integer> listaArticoli();

    void aggiungiAlCarrello(IArticolo articolo, int quantita);

    void rimuoviDalCarrello(IArticolo articolo);

    void svuotaCarrello();
    void acquista(Utente utente, IPagamentoService pagamentoService);
}
