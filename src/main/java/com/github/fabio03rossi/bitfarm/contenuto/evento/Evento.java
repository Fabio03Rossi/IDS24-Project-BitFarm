package com.github.fabio03rossi.bitfarm.contenuto.evento;

import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;
import com.github.fabio03rossi.bitfarm.misc.Posizione;

public class Evento extends Contenuto {
    private String titolo;
    private String descrizione;
    private Posizione posizione;

    Evento(String titolo, String descrizione, Posizione posizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.posizione = posizione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }
}
