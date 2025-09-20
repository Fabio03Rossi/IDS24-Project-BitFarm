package com.github.fabio03rossi.bitfarm.contenuto;

import com.github.fabio03rossi.bitfarm.enums.EStatoContenuto;

import java.util.Date;

public abstract class Contenuto {
    private Integer id;
    private Date dataCreazione;

    private IStatoContenuto stato = new StatoBozza();

    public IStatoContenuto getStato() {
        return stato.getStato();
    }

    public void setStato(IStatoContenuto stato) {
        this.stato = stato;
    }
}
