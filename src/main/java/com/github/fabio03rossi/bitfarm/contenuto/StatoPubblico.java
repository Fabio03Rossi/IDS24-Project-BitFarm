package com.github.fabio03rossi.bitfarm.contenuto;

import com.github.fabio03rossi.bitfarm.enums.EStatoContenuto;

public class StatoPubblico implements IStatoContenuto {
    @Override
    public EStatoContenuto getStato() {
        return EStatoContenuto.PUBBLICO;
    }

    @Override
    public void setStato(IStatoContenuto stato) {

    }
}
