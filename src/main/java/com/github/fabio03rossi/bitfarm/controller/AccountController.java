package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;
import com.github.fabio03rossi.bitfarm.services.AccountService;
import com.github.fabio03rossi.bitfarm.services.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ResponseEntity<Object> creaAccount(@RequestBody UtenteDTO account) {
        this.accountService.registraAccount(
                account.nickname(),
                account.email(),
                account.password(),
                account.indirizzo()
        );
        return new ResponseEntity<>("Utente creato correttamente.", HttpStatus.CREATED);
    }
}
