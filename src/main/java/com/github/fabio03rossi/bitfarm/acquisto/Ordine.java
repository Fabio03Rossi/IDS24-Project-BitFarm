package com.github.fabio03rossi.bitfarm.acquisto;

import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.services.IAccettazioneService;

import java.util.List;

public class Ordine {
    private String indirizzo;
    private IAccettazioneService.IPagamentoService IPagamentoService;
    private double totale;
    private List<IArticolo> listaArticoli;
    private int idUtente;

    public Ordine(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public List<IArticolo> getListaArticoli() {
        return listaArticoli;
    }

    public double getTotale() {
        return totale;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public IAccettazioneService.IPagamentoService getMetodoDiPagamento() {
        return IPagamentoService;
    }
}
