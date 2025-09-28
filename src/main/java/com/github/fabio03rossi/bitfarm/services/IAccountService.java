package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;

public interface IAccountService {
    void registraUtente(UtenteDTO dto);

    void registraGestoreDellaPiattaforma(UtenteDTO dto);

    void registraCuratore(UtenteDTO dto);

    void registraAzienda(AziendaDTO dto);

    boolean modificaAzienda(int id, AziendaDTO dto);

    boolean modificaUtente(int id, UtenteDTO dto);

    boolean modificaCuratore(int id, UtenteDTO dto);

    boolean modificaGestoreDellaPiattaforma(int id, UtenteDTO dto);

    boolean eliminaUtente(int id);

    boolean eliminaAzienda(int id);

    boolean eliminaCuratore(int id);

    boolean eliminaGestoreDellaPiattaforma(int id);

    boolean loginAccount(String email, String password);

    boolean loginAzienda(String nome, String password);

    Azienda getAzienda(int id);
}
