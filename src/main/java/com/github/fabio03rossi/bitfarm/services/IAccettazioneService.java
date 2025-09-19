package com.github.fabio03rossi.bitfarm.services;

public interface IAccettazioneService {
    void accettaContenuto();
    void rifiutaContenuto();

    interface IPagamentoService {
        public void buy();
    }
}
