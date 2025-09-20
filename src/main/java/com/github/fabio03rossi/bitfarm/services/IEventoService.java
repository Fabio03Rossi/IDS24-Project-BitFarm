package com.github.fabio03rossi.bitfarm.services;

import java.util.Date;

public interface IEventoService {
    void creaEvento(int id, String nome, String descrizione, Date data, String posizione);
    void eliminaEvento(int id);
    void modificaEvento(int id, String nome, String descrizione, Date data, String posizione);
}
