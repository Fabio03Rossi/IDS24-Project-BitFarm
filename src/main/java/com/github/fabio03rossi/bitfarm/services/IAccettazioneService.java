package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.acquisto.Carrello;
import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;

import java.util.List;

public interface IAccettazioneService {

    List<Contenuto> getAllRichieste();

    void accettaContenuto(Contenuto r);
    void rifiutaContenuto(Contenuto r);
}
