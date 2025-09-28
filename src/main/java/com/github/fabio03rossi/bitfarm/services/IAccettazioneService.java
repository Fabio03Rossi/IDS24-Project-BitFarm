package com.github.fabio03rossi.bitfarm.services;
import com.github.fabio03rossi.bitfarm.contenuto.IContenuto;

import java.util.List;

public interface IAccettazioneService {

    List<IContenuto> getAllRichieste();

    void accettaArticolo(int id);
    void accettaEvento(int id);
    void rifiutaArticolo(int id);
    void rifiutaEvento(int id);
}
