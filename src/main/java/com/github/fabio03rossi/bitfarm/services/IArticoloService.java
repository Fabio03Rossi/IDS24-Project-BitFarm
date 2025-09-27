package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.dto.PacchettoDTO;
import com.github.fabio03rossi.bitfarm.dto.ProdottoDTO;

public interface IArticoloService {

    void creaArticolo(ProdottoDTO dto);

    void creaPacchetto(PacchettoDTO dto);

    void eliminaArticolo(int id);

    void modificaArticolo(ProdottoDTO dto, int id);

    void modificaArticolo(PacchettoDTO dto, int id);

    void eliminaPacchetto(int id);
}
