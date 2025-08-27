package com.github.fabio03rossi.bitfarm.post;

import java.util.HashMap;

public interface IArticoloComposto extends IArticolo {
    public HashMap<Prodotto, Integer> getProductList();
}
