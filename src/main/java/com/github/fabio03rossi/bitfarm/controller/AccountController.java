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

    @RequestMapping(value = "/creaAccount", method = RequestMethod.POST)
    public ResponseEntity<Object> creaAccount(@RequestBody UtenteDTO account) {
        this.accountService.registraAccount(
                account.nickname(),
                account.email(),
                account.password(),
                account.indirizzo()
        );
        return new ResponseEntity<>("Utente creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/creaAzienda", method = RequestMethod.POST)
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

    @RequestMapping(value = "/modificaAccount/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificaAccount(@PathVariable("id") int id, @RequestBody UtenteDTO account) {
        this.accountService.modificaAccount(
                id,
                account.nickname(),
                account.email(),
                account.password(),
                account.indirizzo()
        );
        return new ResponseEntity<>("Utente modificato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/modificaAzienda/{id}", method = RequestMethod.PUT)
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
}
