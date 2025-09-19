package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.acquisto.Carrello;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;

public class AcquistoService implements IAcquistoService {
    private Carrello carrello;
    private IAccettazioneService.IPagamentoService pagamentoService;

    @Override
    public void aggiungiAlCarrello(IArticolo articolo) {

    }

    @Override
    public void rimuoviDalCarrello(IArticolo articolo) {

    }

    @Override
    public void svuotaCarrello() {

    }

    @Override
    public void acquista() {

    }
}
