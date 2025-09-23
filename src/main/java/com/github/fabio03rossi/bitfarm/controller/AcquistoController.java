package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.account.Account;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;
import com.github.fabio03rossi.bitfarm.misc.Sessione;
import com.github.fabio03rossi.bitfarm.services.IAcquistoService;
import com.github.fabio03rossi.bitfarm.services.IPagamentoService;
import com.github.fabio03rossi.bitfarm.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcquistoController {
    IAcquistoService acquistoService;
    Sessione sessione;

    @Autowired
    public AcquistoController(IAcquistoService acquistoService) {
        this.acquistoService = acquistoService;
    }

    @RequestMapping
    public ResponseEntity<Object> acquista(UtenteDTO utenteDTO, String pagamentoService) {
        if(sessione.isLogged()) {
            IPagamentoService servizio = new PagamentoService();
            Account account = sessione.getAccount();
            if(account instanceof Utente utente) {
                acquistoService.acquista(utente, servizio);
                return new ResponseEntity<>("Acquisto completato con successo", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Il tuo account non è abilitato per gli acquisti", HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>("Non è possibile effettuare acquisti se non si è effettuato prima il login", HttpStatus.UNAUTHORIZED);
    }
}
