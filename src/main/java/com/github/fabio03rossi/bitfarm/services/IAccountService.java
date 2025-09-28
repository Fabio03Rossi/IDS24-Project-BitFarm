package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;

public interface IAccountService {
    void registraUtente(UtenteDTO dto);

    void registraGestoreDellaPiattaforma(UtenteDTO dto);

    void registraCuratore(UtenteDTO dto);

    void registraAzienda(AziendaDTO dto);

    void modificaAzienda(int id, AziendaDTO dto);

    void modificaUtente(int id, UtenteDTO dto);

    void modificaCuratore(int id, UtenteDTO dto);

    void modificaGestoreDellaPiattaforma(int id, UtenteDTO dto);

    void eliminaUtente(int id);

    void eliminaAzienda(int id);

    void eliminaCuratore(int id);

    void eliminaGestoreDellaPiattaforma(int id);

    boolean loginAccount(String email, String password);

    boolean loginAzienda(String nome, String password);

    Azienda getAzienda(int id);
}
