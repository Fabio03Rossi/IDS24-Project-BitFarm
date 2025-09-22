package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventoService implements IEventoService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EventoService.class);
    private final DBManager db = DBManager.getInstance();

    @Override
    public void creaEvento(String nome, String descrizione, Date data, String posizione) {
        var evento = new Evento(nome, descrizione, data, posizione);
        evento.setStato(new StatoValidazione());

        this.db.addEvento(evento);
    }

    @Override
    public void eliminaEvento(int id) {
    }

    @Override
    public void modificaEvento(int id, String nome, String descrizione, Date data, String posizione) {
        try {
            Evento ev = this.db.getEvento(id);
            ev.setNome(nome);
            ev.setDescrizione(descrizione);
            ev.setData(data);
            ev.setPosizione(posizione);
            this.db.updateEvento(ev);
        } catch (Exception ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
