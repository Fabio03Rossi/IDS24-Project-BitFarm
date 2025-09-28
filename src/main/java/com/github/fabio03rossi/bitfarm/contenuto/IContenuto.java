package com.github.fabio03rossi.bitfarm.contenuto;

import com.github.fabio03rossi.bitfarm.enums.EStatoContenuto;

public interface IContenuto {
    public EStatoContenuto getStato();
    public void setStato(IStatoContenuto stato);
}
