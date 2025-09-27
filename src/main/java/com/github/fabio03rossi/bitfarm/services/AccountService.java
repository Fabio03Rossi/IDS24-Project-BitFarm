package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.*;
import com.github.fabio03rossi.bitfarm.database.DBManager;

import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;
import com.github.fabio03rossi.bitfarm.misc.Sessione;
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
    public void registraGestoreDellaPiattaforma(String email, String password, String nome, String indirizzo) {
        GestoreDellaPiattaforma gestore = new GestoreDellaPiattaforma(email, password, nome, indirizzo);
        this.db.addGestoreDellaPiattaforma(gestore);
    }

    @Override
    public boolean loginAccount(String email, String password) {
        Sessione sessione = Sessione.getInstance();
        Account utente = this.db.getUtente(email);
        if(utente.getPassword().equals(password)) {
            sessione.login(utente);
            log.info("Loggato come {}", utente.getEmail());
        }
        return false;
    }

    @Override
    public boolean eliminaUtente(int id) {
        Sessione sessione = Sessione.getInstance();
        if(sessione.getAccount() instanceof GestoreDellaPiattaforma){
            db.cancellaUtente(id);
            log.info("Account eliminato");
            return true;
        }
        log.warn("Il tuo account non ha i permessi necessari per eliminare gli account");
        return false;
    }

    @Override
    public void modificaUtente(int id, String nickname, String email, String password, String indirizzo) {
        try {
            Utente ut = new Utente(nickname, email, password, indirizzo);
            ut.setId(id);
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
    public boolean loginAzienda(String email, String password)
    {
        Sessione sessione = Sessione.getInstance();
        Account account = db.getAzienda(email);
        sessione.login(account);
        return true;
    }

    @Override
    public boolean eliminaAzienda(int id) {
        Sessione sessione = Sessione.getInstance();
        if(sessione.getAccount() instanceof GestoreDellaPiattaforma){
            log.info("Account aziendale eliminato");
            db.cancellaAzienda(id);
        }
        log.warn("Non hai i permessi necessari per eliminare un account aziendale");
        return false;
    }

    @Override
    public boolean modificaAzienda(int id, String partitaIVA, String nome, String email, String password, String descrizione, String indirizzo, String telefono, String tipologia, String certificazioni) {
        Sessione sessione = Sessione.getInstance();
        if(sessione.getAccount() instanceof GestoreDellaPiattaforma){
            try {
                Azienda az = new Azienda(partitaIVA, nome, email, password, descrizione, indirizzo, telefono, tipologia, certificazioni);
                az.setId(id);
                this.db.updateAzienda(az);
                log.info("Account aziendale modificato");
                return true;
            } catch (Exception ex) {
                //throw new... TODO
                log.error("Errore nella modifica dell'azienda, tipo di errore: {}", String.valueOf(ex));
            }
            log.warn("Non hai i permessi necessari per eliminare un account Aziendale");
            return false;
        }
    }

    @Override
    public void registraCuratore(String email, String password) {
        // TODO
        log.warn("registraCuratore non implementato");
    }

    @Override
    public boolean modificaCuratore(int id, String email, String password) {
        // TODO
        Sessione sessione = Sessione.getInstance();
        if(sessione.getAccount() instanceof GestoreDellaPiattaforma){
            db.cancellaCuratore(id);
            log.info("Curatore modificato");
            return true;
        }
        log.warn("Non hai i permessi per eliminare un account di tipo Curatore");
        return false;
    }

    @Override
    public boolean modificaGestoreDellaPiattaforma(int id, String email, String password, String indirizzo) {
        return false;
    }

    @Override
    public boolean eliminaCuratore(int id) {
        // TODO
        log.warn("eliminaCuratore non implementato");
        return false;
    }

    @Override
    public boolean eliminaGestoreDellaPiattaforma(int id) {
        return false;
    }
}
