package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Account;
import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.Curatore;
import com.github.fabio03rossi.bitfarm.account.GestoreDellaPiattaforma;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;
import com.github.fabio03rossi.bitfarm.exception.AccessoNegatoException;
import com.github.fabio03rossi.bitfarm.misc.Sessione;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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

    //-------------------------------------- GET --------------------------------------

    @Override
    public UtenteDTO getUtente(int id) {
        Utente utente = this.db.getUtente(id);

        return utente.toDTO();
    }

    @Override
    public AziendaDTO getAzienda(int id) {
        Azienda azienda = this.db.getAzienda(id);
        return azienda.toDTO();
    }

    @Override
    public UtenteDTO getCuratore(int id) {
        Curatore curatore = this.db.getCuratore(id);
        return curatore.toDTO();
    }

    @Override
    public UtenteDTO getGestoreDellaPiattaforma(int id) {
        GestoreDellaPiattaforma gestore = this.db.getGestoreDellaPiattaforma(id);
        return gestore.toDTO();
    }

    //-------------------------------------- GET_ALL --------------------------------------

    @Override
    public List<UtenteDTO> getAllUtenti() {
        List<UtenteDTO> dto = new ArrayList<>();
        List<Utente> listaUtenti = this.db.getAllUtenti();
        for (Utente utente : listaUtenti) {
        dto.add(utente.toDTO());
        }
        return dto;
    }

    @Override
    public List<AziendaDTO> getAllAziende() {
        List<AziendaDTO> dto = new ArrayList<>();
        List<Azienda> listaAziende = this.db.getAllAziende();
        for (Azienda azienda : listaAziende) {
            dto.add(azienda.toDTO());
        }
        return dto;
    }

    @Override
    public List<UtenteDTO> getAllCuratori() {
        List<UtenteDTO> dto = new ArrayList<>();
        List<Curatore> listaCuratori = db.getAllCuratori();
        for (Curatore curatore : listaCuratori) {
            dto.add(curatore.toDTO());
        }
        return dto;
    }

    @Override
    public List<UtenteDTO> getAllGestori() {
        List<UtenteDTO> dto = new ArrayList<>();
        List<GestoreDellaPiattaforma> listaGestori = db.getAllGestori();
        for (GestoreDellaPiattaforma gestore : listaGestori) {
            dto.add(gestore.toDTO());
        }
        return dto;
    }

    //-------------------------------------- MODIFICA --------------------------------------

    @Override
    public void modificaAzienda(int id, AziendaDTO dto) {
        try {
            Sessione sessione = Sessione.getInstance();
            Account azienda = sessione.getAccount();
            if (azienda instanceof GestoreDellaPiattaforma || azienda.getId() == id) {
                Azienda az = new Azienda(dto.partitaIVA(), dto.nome(), dto.email(), dto.password(), dto.descrizione(), dto.indirizzo(), dto.telefono(), dto.tipologia(), dto.certificazioni());
                az.setId(id);
                this.db.updateAzienda(az);
                log.info("Account dell'azienda modificato");
            } else {
                log.warn("Non disponi dei permessi necessari");
                throw new AccessoNegatoException();
            }
        } catch (Exception ex) {
            log.error("Errore nella modifica dell'account, tipo di errore: {}", String.valueOf(ex));
            throw ex;
        }
    }


    @Override
    public void modificaUtente(int id, UtenteDTO dto) {
        try {
            Sessione sessione = Sessione.getInstance();
            Account utente = sessione.getAccount();
            if (utente instanceof GestoreDellaPiattaforma || utente.getId() == id) {
                Utente ut = new Utente(dto.nickname(), dto.email(), dto.password(), dto.indirizzo());
                ut.setId(id);
                this.db.updateUtente(ut);
                log.info("Account dell'utente modificato");
            } else {
                log.warn("Non disponi dei permessi necessari");
                throw new AccessoNegatoException();
            }
        } catch (Exception ex) {
            log.error("Errore nella modifica dell'account, tipo di errore: {}", String.valueOf(ex));
            throw ex;
        }
    }

    @Override
    public void modificaCuratore(int id, UtenteDTO dto) {
        try {
            Sessione sessione = Sessione.getInstance();
            Account curatore = sessione.getAccount();
            if (curatore instanceof GestoreDellaPiattaforma || curatore.getId() == id) {
                Curatore cu = new Curatore(dto.nickname(), dto.email(), dto.password(), dto.indirizzo());
                cu.setId(id);
                this.db.updateCuratore(cu);
                log.info("Account del curatore modificato");
            } else {
                log.warn("Non disponi dei permessi necessari");
                throw new AccessoNegatoException();
            }
        } catch (Exception ex) {
            log.error("Errore nella modifica del curatore, tipo di errore: {}", String.valueOf(ex));
            throw ex;
        }
    }

    @Override
    public void modificaGestoreDellaPiattaforma(int id, UtenteDTO dto) {
    }

    //-------------------------------------- ELIMINA --------------------------------------

    @Override
    public void eliminaUtente(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof Utente) {
            this.db.cancellaUtente(id);
            log.info("Account utente eliminato");
        } else {
            log.warn("Non disponi dei permessi necessari");
            throw new AccessoNegatoException();
        }
    }

    @Override
    public void eliminaAzienda(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof Azienda) {
            this.db.cancellaAzienda(id);
            log.info("Account azienda eliminato");
        } else {
            log.warn("Non disponi dei permessi necessari");
            throw new AccessoNegatoException();
        }
    }

    @Override
    public void eliminaCuratore(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof Curatore) {
            this.db.cancellaCuratore(id);
            log.info("Account curatore eliminato");
        } else {
            log.warn("Non disponi dei permessi necessari");
            throw new AccessoNegatoException();
        }
    }

    @Override
    public void eliminaGestoreDellaPiattaforma(int id) {
        Sessione sessione = Sessione.getInstance();
        if (sessione.getAccount() instanceof GestoreDellaPiattaforma) {
            this.db.cancellaGestoreDellaPiattaforma(id);
            log.info("Account gestoreDellaPiattaforma eliminato");
        } else {
            log.warn("Non disponi dei permessi necessari");
            throw new AccessoNegatoException();
        }
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
        Account account = this.db.getAzienda(email);
        if (account.getPassword().equals(password)) {
            sessione.login(account);
            log.info("Loggato come {}", account.getEmail());
        }
        return true;
    }

    @Override
    public boolean loginCuratore(String email, String password) {
        Sessione sessione = Sessione.getInstance();
        Account account = this.db.getCuratore(email);
        if (account.getPassword().equals(password)) {
            sessione.login(account);
            log.info("Loggato come {}", account.getEmail());
        }
        return true;
    }

    @Override
    public boolean loginGestore(String email, String password) {
        Sessione sessione = Sessione.getInstance();
        Account account = this.db.getGestoreDellaPiattaforma(email);
        if (account.getPassword().equals(password)) {
            sessione.login(account);
            log.info("Loggato come {}", account.getEmail());
        }
        return true;
    }

    @Override
    public void logout() {
        Sessione sessione = Sessione.getInstance();
        sessione.logout();
    }
}

