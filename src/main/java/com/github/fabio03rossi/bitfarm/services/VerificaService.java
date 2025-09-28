package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerificaService implements IVerificaService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(VerificaService.class);
    private final DBManager db;

    public VerificaService() {
        db = DBManager.getInstance();
    }

    @Override
    public List<? extends Azienda> getAllRichieste() {
        return this.db.getAllAziende();
    }


    @Override
    public void accettaRegistrazioneAzienda(int id) {
        //this.db.aziend(id);
    }

    @Override
    public void rifiutaRegistrazioneAzienda(int id) {
        //this.db.rifiutaAzienda(id);
    }
}
