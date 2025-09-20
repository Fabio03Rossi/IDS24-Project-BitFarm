package com.github.fabio03rossi.bitfarm.services;

public interface IArticoloService {
    void creaArticolo(int id, String nome, String descrizione, Double prezzo, String certs);

    void creaPacchetto(int id, String nome, String descrizione, Double prezzo, String certs);

    void eliminaArticolo();
    void modificaArticolo();

    void eliminaPacchetto();

    void modificaPacchetto();
}
