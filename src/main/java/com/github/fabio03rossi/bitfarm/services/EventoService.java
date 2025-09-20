package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.database.DBManager;

import java.sql.SQLException;
import java.util.Date;

public class EventoService implements IEventoService {
    private final DBManager db = DBManager.getInstance();

    @Override
    public void creaEvento(int id, String nome, String descrizione, Date data, String posizione) {
        try {
            var evento = new Evento(id, nome, descrizione, data, posizione);
            evento.setStato(new StatoValidazione());

            this.db.setEvento(evento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminaEvento(int id) {

    }

    @Override
    public void modificaEvento(int id, String nome, String descrizione, Date data, String posizione) {
    }
}
