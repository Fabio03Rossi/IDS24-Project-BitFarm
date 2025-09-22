package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.acquisto.Carrello;
import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;

import java.util.List;

public interface IAccettazioneService {

    List<Contenuto> getAllRichieste();

    void accettaArticolo(int id);
    void accettaEvento(int id);
    void rifiutaArticolo(int id);
    void rifiutaEvento(int id);
}
