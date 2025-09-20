package com.github.fabio03rossi.bitfarm.contenuto;

import com.github.fabio03rossi.bitfarm.enums.EStatoContenuto;

public class StatoValidazione implements IStatoContenuto {
    @Override
    public EStatoContenuto getStato() {
        return EStatoContenuto.VALIDAZIONE;
    }

    @Override
    public void setStato(IStatoContenuto stato) {

    }
}
