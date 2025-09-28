package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;
import com.github.fabio03rossi.bitfarm.services.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

    final String PATH = "account";
    
    @Autowired
    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/" + PATH + "/creaUtente", method = RequestMethod.POST)
    public ResponseEntity<Object> creaUtente(@Valid @RequestBody UtenteDTO account) {
        this.accountService.registraUtente(account);
        return new ResponseEntity<>("Utente creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/" + PATH + "/creaAzienda", method = RequestMethod.POST)
    public ResponseEntity<Object> creaAzienda(@Valid @RequestBody AziendaDTO account) {
        this.accountService.registraAzienda(account);
        return new ResponseEntity<>("Azienda creata correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/" + PATH + "/creaCuratore", method = RequestMethod.POST)
    public ResponseEntity<Object> creaCuratore(@Valid @RequestBody UtenteDTO curatoreDTO) {
        this.accountService.registraCuratore(curatoreDTO);
        return new ResponseEntity<>("Curatore creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/" + PATH + "/creaGestoreDellaPiattaforma", method = RequestMethod.PUT)
    public ResponseEntity<Object> creaGestoreDellaPiattaforma(@Valid @RequestBody UtenteDTO account) {
        this.accountService.registraGestoreDellaPiattaforma(account);
        return new ResponseEntity<>("Account Gestore creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/" + PATH + "/modificaUtente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificaUtente(@PathVariable("id") int id, @Valid @RequestBody UtenteDTO account) {
        this.accountService.modificaUtente(id, account);
        return new ResponseEntity<>("Utente modificato correttamente.", HttpStatus.OK);

    }

    @RequestMapping(value = "/" + PATH + "/modificaAzienda/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificaAzienda(@PathVariable("id") int id, @Valid @RequestBody AziendaDTO account) {
        this.accountService.modificaAzienda(id, account);
        return new ResponseEntity<>("Azienda modificata correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/modificaCuratore/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificaCuratore(@PathVariable("id") int id, @Valid @RequestBody UtenteDTO dto) {
        this.accountService.modificaCuratore(id, dto);
        return new ResponseEntity<>("Curatore modificato correttamente.", HttpStatus.OK);
    }


    @RequestMapping(value = "/" + PATH + "/eliminaUtente/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaUtente(@PathVariable("id") int id) {
        this.accountService.eliminaUtente(id);
        return new ResponseEntity<>("Utente eliminato.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/eliminaAzienda/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaAzienda(@PathVariable("id") int id) {
        this.accountService.eliminaAzienda(id);
        return new ResponseEntity<>("Azienda eliminata.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/eliminaCuratore/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaCuratore(@PathVariable("id") int id) {
        this.accountService.eliminaCuratore(id);
        return new ResponseEntity<>("Curatore eliminato.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/loginUtente", method = RequestMethod.POST)
    public ResponseEntity<Object> loginUtente(@Valid @Email String email, @Valid String password) {
        this.accountService.loginAccount(email, password);
        return new ResponseEntity<>("Login avvenuto con successo.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/loginAzienda", method = RequestMethod.POST)
    public ResponseEntity<Object> loginAzienda(@Valid @Email String email, @Valid String password) {
        this.accountService.loginAzienda(email, password);
        return new ResponseEntity<>("Login avvenuto con successo.", HttpStatus.OK);
    }

    //--------------------------- GET ---------------------------

    @RequestMapping(value = "/" + PATH + "/getUtente/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUtente(@PathVariable("id") int id) {
        var dto = this.accountService.getUtente(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getAzienda/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAzienda(@PathVariable("id") int id) {
        var dto = this.accountService.getAzienda(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getCuratore/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCuratore(@PathVariable("id") int id) {
        var dto = this.accountService.getCuratore(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getGestore/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getGestore(@PathVariable("id") int id) {
        var dto = this.accountService.getGestoreDellaPiattaforma(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //--------------------------- GET_ALL ---------------------------

    @RequestMapping(value = "/" + PATH + "/getUtente/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllUtenti() {
        var dto = this.accountService.getAllUtenti();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getAzienda/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAzienda(@PathVariable("id") int id) {
        var dto = this.accountService.getAzienda(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getCuratore/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCuratore(@PathVariable("id") int id) {
        var dto = this.accountService.getCuratore(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getGestore/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getGestore(@PathVariable("id") int id) {
        var dto = this.accountService.getGestoreDellaPiattaforma(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
