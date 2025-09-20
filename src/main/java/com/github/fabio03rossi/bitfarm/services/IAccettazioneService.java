package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;
import com.github.fabio03rossi.bitfarm.contenuto.RichiestaAccettazione;

import java.util.List;

public interface IAccettazioneService {

    List<Contenuto> getAllRichieste();

    void accettaContenuto(Contenuto r);
    void rifiutaContenuto(Contenuto r);

    interface IPagamentoService {
        public void buy();
    }
}
