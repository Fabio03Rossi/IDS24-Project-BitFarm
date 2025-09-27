package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import com.github.fabio03rossi.bitfarm.dto.EventoDTO;
import com.github.fabio03rossi.bitfarm.exception.ErroreInputDatiException;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
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
        if(id == 0) throw new ErroreInputDatiException("ID non valido.");

        this.db.cancellaEvento(id);
    }

    @Override
    public void modificaEvento(int id, EventoDTO dto) {
        if(id == 0) throw new ErroreInputDatiException("Dati in input non validi.");

        Evento ev = new Evento(dto.nome(), dto.descrizione(), dto.data(), dto.posizione());
        ev.setId(id);
        this.db.updateEvento(ev);
    }
}
