package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.database.DBManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AccountService implements IAccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final DBManager db;

    public AccountService() {
        this.db = DBManager.getInstance();
    }

    //-------------------------------------- UTENTE --------------------------------------

    @Override
    public void registraUtente(String nickname, String email, String password, String indirizzo) {
        var utente = new Utente(nickname, email, password, indirizzo);
        this.db.addUtente(utente);
    }



    @Override
    public boolean loginAccount(String email, String password) {
        return false;
    }

    @Override
    public void eliminaUtente(int id) {
        db.cancellaUtente(id);
    }

    @Override
    public void modificaUtente(int id, String nickname, String email, String password, String indirizzo) {
        try {
            Utente ut = new Utente(nickname, email, password, indirizzo);
            this.db.updateUtente(ut);
        } catch (Exception ex) {
            log.error("Errore nella modifica dell'account, tipo di errore: {}", String.valueOf(ex));
        }
    }

    @Override
    public void registraAzienda(String partitaIVA, String nome, String email, String password, String descrizione, String indirizzo, String telefono, String tipologia, String certificazioni) {
        var azienda = new Azienda(partitaIVA, nome, email, password, descrizione, indirizzo, telefono, tipologia, certificazioni);
        this.db.addAzienda(azienda);
    }

    @Override
    public boolean loginAzienda(String nome, String password) {
        return false;
    }

    @Override
    public void eliminaAzienda(int id) {
        db.cancellaAzienda(id);
    }

    @Override
    public void modificaAzienda(int id, String partitaIVA, String nome, String email, String password, String descrizione, String indirizzo, String telefono, String tipologia, String certificazioni) {
        try {
            Azienda az = new Azienda(partitaIVA, nome, email, password, descrizione, indirizzo, telefono, tipologia, certificazioni);
            az.setId(id);
            this.db.updateAzienda(az);
        } catch (Exception ex) {
            log.error("Errore nella modifica dell'azienda, tipo di errore: {}", String.valueOf(ex));
        }
    }

    @Override
    public void registraCuratore(String email, String password) {
        // TODO
        log.warn("non implementato");
    }
}
