package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.Curatore;
import com.github.fabio03rossi.bitfarm.account.GestoreDellaPiattaforma;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;

import java.util.List;

public interface IAccountService {
    void registraUtente(UtenteDTO dto);

    void registraGestoreDellaPiattaforma(UtenteDTO dto);

    void registraCuratore(UtenteDTO dto);

    void registraAzienda(AziendaDTO dto);

    UtenteDTO getUtente(int id);

    AziendaDTO getAzienda(int id);

    UtenteDTO getCuratore(int id);

    UtenteDTO getGestoreDellaPiattaforma(int id);

    List<UtenteDTO> getAllUtenti();

    List<AziendaDTO> getAllAzinde();

    List<UtenteDTO> getAllCuratori();

    List<UtenteDTO> getAllGestori();

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

}
