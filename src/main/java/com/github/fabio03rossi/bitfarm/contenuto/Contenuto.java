package com.github.fabio03rossi.bitfarm.contenuto;

import java.util.Date;

public abstract class Contenuto {
    private Integer id;
    private Date dataCreazione;

    private IStatoContenuto stato;

    public IStatoContenuto getStato() {
        return stato.getStato();
    }

    public void setStato(IStatoContenuto stato) {
        this.stato = stato;
    }
}
