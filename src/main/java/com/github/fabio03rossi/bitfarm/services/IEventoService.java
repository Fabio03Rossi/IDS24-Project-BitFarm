package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.dto.EventoDTO;

import java.util.List;

public interface IEventoService {

    void creaEvento(EventoDTO dto);

    void eliminaEvento(int id);

    void modificaEvento(int id, EventoDTO evento);

    Evento getEvento(int id);

    List<Evento> getAllEventi();
}
