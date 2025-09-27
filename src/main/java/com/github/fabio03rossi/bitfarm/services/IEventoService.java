package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.dto.EventoDTO;

public interface IEventoService {

    void creaEvento(EventoDTO dto);

    void eliminaEvento(int id);

    void modificaEvento(int id, EventoDTO evento);
}
