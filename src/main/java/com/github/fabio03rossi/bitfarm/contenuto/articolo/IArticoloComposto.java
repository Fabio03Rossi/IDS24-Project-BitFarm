package com.github.fabio03rossi.bitfarm.contenuto.articolo;

import java.util.HashMap;

public interface IArticoloComposto extends IArticolo {
    public HashMap<IArticolo, Integer> getListaProdotti();
}
