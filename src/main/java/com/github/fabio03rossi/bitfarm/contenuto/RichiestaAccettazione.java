package com.github.fabio03rossi.bitfarm.contenuto;

import java.util.Date;

public class RichiestaAccettazione {
    private Contenuto contenuto;
    private Date dataAccettazione = null;

    public RichiestaAccettazione(Contenuto contenuto) {
        this.contenuto = contenuto;
    }
}
