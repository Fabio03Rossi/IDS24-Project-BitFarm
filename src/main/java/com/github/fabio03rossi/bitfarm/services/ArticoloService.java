package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Pacchetto;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.database.DBManager;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticoloService implements IArticoloService {
    private final DBManager db = DBManager.getInstance();

    @Override
    public void creaArticolo(String nome, String descrizione, Double prezzo, String certs) {
        try {
            var articolo = new Prodotto(nome, descrizione, prezzo, certs);
            articolo.setStato(new StatoValidazione());

            this.db.addArticolo(articolo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void creaPacchetto(String nome, String descrizione, Double prezzo, String certs) {
        try {
            var pacchetto = new Pacchetto(nome, descrizione, prezzo, certs);
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
    public void modificaArticolo(int id) {
        try {
            IArticolo prodotto = this.db.getArticolo(id);
            this.db.updateArticolo(prodotto);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminaPacchetto() {

    }

    @Override
    public void modificaPacchetto(int id) {
        try {
            IArticolo pacchetto = this.db.getArticolo(id);
            this.db.updateArticolo(pacchetto);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
