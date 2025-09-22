package com.github.fabio03rossi.bitfarm.acquisto;

import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;

import java.util.HashMap;


public class Carrello {
    private HashMap<IArticolo, Integer> listaArticolo;
    private double totale;

    public HashMap<IArticolo, Integer> getListaArticolo() {
        return listaArticolo;
    }

    public void setListaArticolo(HashMap<IArticolo, Integer> listaArticolo) {
        this.listaArticolo = listaArticolo;
    }

    public void svuotaCarrello() {
        this.listaArticolo.clear();
        this.totale = 0;
    }

    public void addArticolo(IArticolo articolo, int quantita) {
        this.listaArticolo.put(articolo, 0);
        totale += articolo.getPrezzo() * quantita;
    }

    public void rimuoviArticolo(IArticolo articolo) {
        totale -= articolo.getPrezzo();
        this.listaArticolo.replace(articolo, this.listaArticolo.get(articolo) - 1);
    }

    public double getTotale() {
        return totale;
    }


    public Carrello() {}


}
