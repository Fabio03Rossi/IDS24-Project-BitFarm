package com.github.fabio03rossi.bitfarm.post;

import com.github.fabio03rossi.bitfarm.misc.IStatoPost;

import java.util.Date;

public abstract class Post {
    private Integer id;
    private Date dataCreazione;

    private IStatoPost stato;

    public IStatoPost getStato() {
        return stato.getStato();
    }

    public void setStato(IStatoPost stato) {
        this.stato = stato;
    }
}
