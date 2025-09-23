package com.github.fabio03rossi.bitfarm.acquisto;

import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;

import java.util.HashMap;


public class Carrello {
    private HashMap<IArticolo, Integer> listaArticolo;
    private double totale;

    public Carrello(){
        this.listaArticolo = new HashMap<>();
    }

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
        totale += articolo.getPrezzo() * quantita;
        if(this.listaArticolo.isEmpty() || this.listaArticolo.get(articolo) == null){
            this.listaArticolo.put(articolo, quantita);
        }else{
            this.listaArticolo.replace(articolo, this.listaArticolo.get(articolo) + quantita);
        }
    }

    public void rimuoviArticolo(IArticolo articolo) {
        totale -= articolo.getPrezzo();
        if(this.listaArticolo.isEmpty() || this.listaArticolo.get(articolo) == null) return;

        this.listaArticolo.replace(articolo, this.listaArticolo.get(articolo) - 1);
    }

    public double getTotale() {
        return totale;
    }

}
