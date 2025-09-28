package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import com.github.fabio03rossi.bitfarm.dto.EventoDTO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoService implements IEventoService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EventoService.class);
    private final DBManager db = DBManager.getInstance();

    @Override
    public void creaEvento(EventoDTO dto) {
        Evento evento = new Evento(dto.nome(), dto.descrizione(), dto.data(), dto.posizione());
        evento.setStato(new StatoValidazione());

        this.db.addEvento(evento);
    }

    @Override
    public void eliminaEvento(int id) {
        this.db.cancellaEvento(id);
    }

    @Override
    public void modificaEvento(int id, EventoDTO dto) {
        Evento ev = new Evento(dto.nome(), dto.descrizione(), dto.data(), dto.posizione());
        ev.setId(id);
        this.db.updateEvento(ev);
    }

    @Override
    public Evento getEvento(int id) {
        return this.db.getEvento(id);
    }

    @Override
    public List<Evento> getAllEventi() {
        return this.db.getAllEventi();
    }

    @Override
    public List<Evento> getEventiAccettati() {
        return this.db.getAllEventiAccettati();
    }
}
