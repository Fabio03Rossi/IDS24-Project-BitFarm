package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;

public interface IAcquistoService {
    void aggiungiAlCarrello(IArticolo articolo);
    void rimuoviDalCarrello(IArticolo articolo);
    void svuotaCarrello();
    void acquista();
}
