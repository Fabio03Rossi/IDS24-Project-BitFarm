package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.*;
import com.github.fabio03rossi.bitfarm.database.DBManager;

import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
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

    //-------------------------------------- REGISTRAZIONI --------------------------------------

    @Override
    public void registraUtente(UtenteDTO dto) {
        var utente = new Utente(dto.nickname(), dto.email(), dto.password(), dto.indirizzo());
        this.db.addUtente(utente);
    }

    @Override
    public void registraAzienda(AziendaDTO dto) {
        var azienda = new Azienda(dto.partitaIVA(), dto.nome(), dto.email(), dto.password(), dto.descrizione(), dto.indirizzo(), dto.telefono(), dto.tipologia(), dto.certificazioni());
        this.db.addAzienda(azienda);
    }

    @Override
    public void registraCuratore(String email, String password) {
        // TODO
        log.warn("registraCuratore non implementato");
    }

    @Override
    public void registraGestoreDellaPiattaforma(UtenteDTO dto) {
        GestoreDellaPiattaforma gestore = new GestoreDellaPiattaforma(dto.email(), dto.password(), dto.nickname(), dto.indirizzo());
        this.db.addGestoreDellaPiattaforma(gestore);
    }

    //-------------------------------------- MODIFICA --------------------------------------
    @Override
    public boolean modificaAzienda(int id, AziendaDTO dto) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            try {
                Azienda az = new Azienda(dto.partitaIVA(), dto.nome(), dto.email(), dto.password(), dto.descrizione(), dto.indirizzo(), dto.telefono(), dto.tipologia(), dto.certificazioni());
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
        return false;
    }


    @Override
    public boolean loginAccount(String email, String password) {
        Sessione sessione = Sessione.getInstance();
        Account utente = this.db.getUtente(email);
        if (utente.getPassword().equals(password)) {
            sessione.login(utente);
            log.info("Loggato come {}", utente.getEmail());
        }
        return false;
    }

    @Override
    public boolean eliminaUtente(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            db.cancellaUtente(id);
            log.info("Account eliminato");
            return true;
        }
        log.warn("Il tuo account non ha i permessi necessari per eliminare gli account");
        return false;
    }

    @Override
    public boolean modificaUtente(int id, UtenteDTO dto) {
        try {
            Sessione sessione = Sessione.getInstance();
            Account utente = sessione.getAccount();
            if (utente instanceof GestoreDellaPiattaforma || utente.getId() == id) {
                Utente ut = new Utente(dto.nickname(), dto.email(), dto.password(), dto.indirizzo());
                ut.setId(id);
                this.db.updateUtente(ut);
                log.info("Account dell'utente modificato");
                return true;
            }
            log.warn("Non hai i permessi necessari per modificare l'account dell'utente.");
            return false;
        } catch (Exception ex) {
            log.error("Errore nella modifica dell'account, tipo di errore: {}", String.valueOf(ex));
            throw ex;
        }
    }

    @Override
    public boolean modificaCuratore(int id, String email, String password) {
        // TODO
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            db.cancellaCuratore(id);
            log.info("Curatore modificato");
            return true;
        }
        log.warn("Non hai i permessi per eliminare un account di tipo Curatore");
        return false;
    }

    @Override
    public boolean modificaGestoreDellaPiattaforma(int id, UtenteDTO dto) {
        return false;
    }

    //-------------------------------------- ELIMININA --------------------------------------

    @Override
    public boolean eliminaAzienda(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            log.info("Account aziendale eliminato");
            db.cancellaAzienda(id);
        }
        log.warn("Non hai i permessi necessari per eliminare un account aziendale");
        return false;
    }

    @Override
    public boolean eliminaCuratore(int id) {
        // TODO continua
        log.warn("eliminaCuratore non implementato");
        return false;
    }

    @Override
    public boolean eliminaGestoreDellaPiattaforma(int id) {
        return false;
    }

    //-------------------------------------- ALTRO --------------------------------------

    @Override
    public boolean loginAzienda(String email, String password) {
        Sessione sessione = Sessione.getInstance();
        Account account = db.getAzienda(email);
        sessione.login(account);
        return true;
    }
}

