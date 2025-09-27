package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;
import com.github.fabio03rossi.bitfarm.services.AccountService;
import com.github.fabio03rossi.bitfarm.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    IAccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/account/creaUtente", method = RequestMethod.POST)
    public ResponseEntity<Object> creaUtente(@RequestBody UtenteDTO account) {
        this.accountService.registraUtente(
                account.nickname(),
                account.email(),
                account.password(),
                account.indirizzo()
        );
        return new ResponseEntity<>("Utente creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/account/creaAzienda", method = RequestMethod.POST)
    public ResponseEntity<Object> creaAzienda(@RequestBody AziendaDTO account) {
        this.accountService.registraAzienda(
                account.partitaIVA(),
                account.nome(),
                account.email(),
                account.password(),
                account.descrizione(),
                account.indirizzo(),
                account.telefono(),
                account.tipologia(),
                account.certificazioni()
        );
        return new ResponseEntity<>("Azienda creata correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/account/creaCuratore", method = RequestMethod.POST)
    public ResponseEntity<Object> creaCuratore(@RequestBody String email, @RequestBody String password) {
        this.accountService.registraCuratore(email, password);
        return new ResponseEntity<>("Curatore creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/account/creaGestoreDellaPiattaforma", method = RequestMethod.PUT)
    public ResponseEntity<Object> creaGestoreDellaPiattaforma(@RequestBody UtenteDTO utenteDTO) {
        this.accountService.registraGestoreDellaPiattaforma(utenteDTO.email(), utenteDTO.password(), utenteDTO.nickname(), utenteDTO.indirizzo());
        return new ResponseEntity<>("Account Gestore creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/account/modificaUtente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificaUtente(@PathVariable("id") int id, @RequestBody UtenteDTO account) {
        this.accountService.modificaUtente(
                id,
                account.nickname(),
                account.email(),
                account.password(),
                account.indirizzo()
        );
        return new ResponseEntity<>("Utente modificato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/account/modificaAzienda/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificaAzienda(@PathVariable("id") int id, @RequestBody AziendaDTO account) {
        this.accountService.modificaAzienda(
                id,
                account.partitaIVA(),
                account.nome(),
                account.email(),
                account.password(),
                account.descrizione(),
                account.indirizzo(),
                account.telefono(),
                account.tipologia(),
                account.certificazioni()
        );
        return new ResponseEntity<>("Azienda modificata correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/account/modificaCuratore/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificaCuratore(@PathVariable("id") int id, @RequestBody String email, @RequestBody String password) {
        this.accountService.modificaCuratore(id, email, password);
        return new ResponseEntity<>("Curatore modificato correttamente.", HttpStatus.OK);
    }


    @RequestMapping(value = "/account/eliminaUtente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaUtente(@PathVariable("id") int id) {
        this.accountService.eliminaUtente(id);
        return new ResponseEntity<>("Utente eliminato.", HttpStatus.OK);
    }

    @RequestMapping(value = "/account/eliminaAzienda/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaAzienda(@PathVariable("id") int id) {
        this.accountService.eliminaAzienda(id);
        return new ResponseEntity<>("Azienda eliminata.", HttpStatus.OK);
    }

    @RequestMapping(value = "/account/eliminaCuratore/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaCuratore(@PathVariable("id") int id) {
        this.accountService.eliminaCuratore(id);
        return new ResponseEntity<>("Curatore eliminato.", HttpStatus.OK);
    }

}
