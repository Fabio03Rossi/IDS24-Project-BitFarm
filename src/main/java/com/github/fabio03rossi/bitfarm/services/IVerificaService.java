package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Azienda;

import java.util.List;

public interface IVerificaService {
    List<? extends Azienda> getAllRichieste();

    void accettaRegistrazioneAzienda(int id);

    void rifiutaRegistrazioneAzienda(int id);
}
