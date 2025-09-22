package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;

public interface IAcquistoService {
    void aggiungiAlCarrello(IArticolo articolo, int quantita);
    void rimuoviDalCarrello(IArticolo articolo, int quantita);
    void svuotaCarrello();
    void acquista(Utente utente);
}
