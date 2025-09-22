package com.github.fabio03rossi.bitfarm.services;

public interface IArticoloService {

    void creaArticolo(String nome, String descrizione, Double prezzo, String certs);
    void creaPacchetto(String nome, String descrizione, Double prezzo, String certs);

    void eliminaArticolo(int id);

    void modificaArticolo(int id, String nome, String descrizione, Double prezzo, String certs);

    void eliminaPacchetto(int id);

    void modificaPacchetto(int id, String nome, String descrizione, Double prezzo, String certs);
}
