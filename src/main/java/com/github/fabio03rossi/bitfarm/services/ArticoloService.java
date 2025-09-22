package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Pacchetto;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.database.DBManager;

import java.sql.SQLException;

public class ArticoloService implements IArticoloService {
    private final DBManager db = DBManager.getInstance();

    @Override
    public void creaArticolo(int id, String nome, String descrizione, Double prezzo, String certs) {
        try {
            var articolo = new Prodotto(id, nome, descrizione, prezzo, certs);
            articolo.setStato(new StatoValidazione());

            this.db.addArticolo(articolo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void creaPacchetto(int id, String nome, String descrizione, Double prezzo, String certs) {
        try {
            var pacchetto = new Pacchetto(id, nome, descrizione, prezzo, certs);
            pacchetto.setStato(new StatoValidazione());

            this.db.addArticolo(pacchetto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminaArticolo() {

    }

    @Override
    public void modificaArticolo() {

    }

    @Override
    public void eliminaPacchetto() {

    }

    @Override
    public void modificaPacchetto() {

    }
}
