package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Pacchetto;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import com.github.fabio03rossi.bitfarm.dto.PacchettoDTO;
import com.github.fabio03rossi.bitfarm.dto.ProdottoDTO;
import org.slf4j.LoggerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticoloService implements IArticoloService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ArticoloService.class);
    private final DBManager db = DBManager.getInstance();

    @Override
    public void creaArticolo(String nome, String descrizione, Double prezzo, String certs) {
        var articolo = new Prodotto(nome, descrizione, prezzo, certs);
        articolo.setStato(new StatoValidazione());

        this.db.addArticolo(articolo);
    }

    @Override
    public void creaPacchetto(String nome, String descrizione, Double prezzo, String certs) {
        var pacchetto = new Pacchetto(nome, descrizione, prezzo, certs);
        pacchetto.setStato(new StatoValidazione());

        this.db.addArticolo(pacchetto);
    }

    @Override
    public void eliminaArticolo(int id) {

    }

    @Override
    public void modificaArticolo(ProdottoDTO dto, int id) {
        try {
            IArticolo articolo = new Prodotto(dto.nome(), dto.descrizione(), dto.prezzo(), dto.certificazioni());
            this.db.updateArticolo(articolo);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modificaArticolo(PacchettoDTO dto, int id) {
        try {
            IArticolo articolo = new Pacchetto(dto.nome(), dto.descrizione(), dto.prezzo(), dto.certificazioni());
            this.db.updateArticolo(articolo);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminaPacchetto(int id) {

    }

}
