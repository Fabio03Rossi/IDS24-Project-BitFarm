package com.github.fabio03rossi.bitfarm.contenuto;

import com.github.fabio03rossi.bitfarm.enums.EStatoContenuto;

public class StatoBozza implements IStatoContenuto {
    @Override
    public EStatoContenuto getStato() {
        return EStatoContenuto.BOZZA;
    }

    @Override
    public void setStato(IStatoContenuto stato) {

    }
}
