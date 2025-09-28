package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Account;
import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.GestoreDellaPiattaforma;
import com.github.fabio03rossi.bitfarm.account.Utente;
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
    public void registraCuratore(UtenteDTO curatoreDTO) {
        Curatore curatore = new Curatore(curatoreDTO.email(), curatoreDTO.password(), curatoreDTO.nickname(), curatoreDTO.indirizzo());
        this.db.addCuratore(curatore);
    }

    @Override
    public void registraGestoreDellaPiattaforma(UtenteDTO dto) {
        GestoreDellaPiattaforma gestore = new GestoreDellaPiattaforma(dto.email(), dto.password(), dto.nickname(), dto.indirizzo());
        this.db.addGestoreDellaPiattaforma(gestore);
    }

    //-------------------------------------- MODIFICA --------------------------------------
    @Override
    public boolean modificaAzienda(int id, AziendaDTO dto) {
        try {
            Sessione sessione = Sessione.getInstance();
            Account azienda = sessione.getAccount();
            if (azienda instanceof GestoreDellaPiattaforma || azienda.getId() == id) {
                Azienda az = new Azienda(dto.partitaIVA(), dto.nome(), dto.email(), dto.password(), dto.descrizione(), dto.indirizzo(), dto.telefono(), dto.tipologia(), dto.certificazioni());
                az.setId(id);
                this.db.updateAzienda(az);
                log.info("Account dell'azienda modificato");
                return true;
            }
            log.warn("Non hai i permessi necessari per modificare l'account dell'azienda.");
            return false;
        } catch (Exception ex) {
            log.error("Errore nella modifica dell'account, tipo di errore: {}", String.valueOf(ex));
            throw ex;
        }
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
    public boolean modificaCuratore(int id, UtenteDTO dto) {
        try {
            Sessione sessione = Sessione.getInstance();
            Account curatore = sessione.getAccount();
            if (curatore instanceof GestoreDellaPiattaforma || curatore.getId() == id) {
                Curatore cu = new Curatore(dto.nickname(), dto.email(), dto.password(), dto.indirizzo());
                cu.setId(id);
                this.db.updateCuratore(cu);
                log.info("Account del curatore modificato");
                return true;
            }
            log.warn("Non hai i permessi necessari per modificare l'account del curatore.");
            return false;
        } catch (Exception ex) {
            log.error("Errore nella modifica del curatore, tipo di errore: {}", String.valueOf(ex));
            throw ex;
        }
    }

    @Override
    public boolean modificaGestoreDellaPiattaforma(int id, UtenteDTO dto) {
        return false;
    }

    //-------------------------------------- ELIMININA --------------------------------------

    @Override
    public boolean eliminaUtente(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            db.cancellaGestoreDellaPiattaforma(id);
            log.info("Account gestoreDellaPiattaforma eliminato");
            return true;
        }

        log.warn("Non disponi dei permessi necessari");
        return false;
    }

    @Override
    public boolean eliminaAzienda(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            db.cancellaGestoreDellaPiattaforma(id);
            log.info("Account gestoreDellaPiattaforma eliminato");
            return true;
        }

        log.warn("Non disponi dei permessi necessari");
        return false;
    }

    @Override
    public boolean eliminaCuratore(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            db.cancellaGestoreDellaPiattaforma(id);
            log.info("Account gestoreDellaPiattaforma eliminato");
            return true;
        }

        log.warn("Non disponi dei permessi necessari");
        return false;
    }

    @Override
    public boolean eliminaGestoreDellaPiattaforma(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            db.cancellaGestoreDellaPiattaforma(id);
            log.info("Account gestoreDellaPiattaforma eliminato");
            return true;
        }

        log.warn("Non disponi dei permessi necessari");
        return false;
    }

    //-------------------------------------- ALTRO --------------------------------------

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
    public boolean loginAzienda(String email, String password) {
        Sessione sessione = Sessione.getInstance();
        Account account = db.getAzienda(email);
        sessione.login(account);
        return true;
    }

    @Override
    public Azienda getAzienda(int id) {
        return this.db.getAzienda(id);
    }
}

