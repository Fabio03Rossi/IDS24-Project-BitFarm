package com.github.fabio03rossi.bitfarm.services;

import java.sql.SQLException;

public interface IArticoloService {

    void creaArticolo(String nome, String descrizione, Double prezzo, String certs);
    void creaPacchetto(String nome, String descrizione, Double prezzo, String certs);

    void eliminaArticolo();
    void modificaArticolo(int id);

    void eliminaPacchetto();
    void modificaPacchetto(int id);
}
