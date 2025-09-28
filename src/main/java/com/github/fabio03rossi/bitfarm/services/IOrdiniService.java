package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.acquisto.Ordine;

public interface IOrdiniService {

    Ordine getOrdine(int id);

    void setOrdine(Ordine ordine);

    void cancellaOrdine(Ordine ordine);

    void cancellaOrdine(int id);
}
