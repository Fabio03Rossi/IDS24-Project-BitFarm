package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.RichiestaAccettazione;

import java.util.List;

public interface IAccettazioneService {

    List<RichiestaAccettazione> getAllRichieste();

    void accettaContenuto(RichiestaAccettazione r);
    void rifiutaContenuto(RichiestaAccettazione r);

    interface IPagamentoService {
        public void buy();
    }
}
