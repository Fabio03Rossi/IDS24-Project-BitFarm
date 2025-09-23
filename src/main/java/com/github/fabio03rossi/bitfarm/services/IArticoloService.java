package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.dto.PacchettoDTO;
import com.github.fabio03rossi.bitfarm.dto.ProdottoDTO;

public interface IArticoloService {

    void creaArticolo(String nome, String descrizione, Double prezzo, String certs);

    void creaPacchetto(String nome, String descrizione, Double prezzo, String certs);

    void eliminaArticolo(int id);

    void modificaArticolo(ProdottoDTO dto, int id);

    void modificaArticolo(PacchettoDTO dto, int id);

    void eliminaPacchetto(int id);
}
