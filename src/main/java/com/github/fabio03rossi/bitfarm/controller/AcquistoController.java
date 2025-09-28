package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.account.Account;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.dto.ProdottoDTO;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;
import com.github.fabio03rossi.bitfarm.misc.Sessione;
import com.github.fabio03rossi.bitfarm.services.IAcquistoService;
import com.github.fabio03rossi.bitfarm.services.IPagamentoService;
import com.github.fabio03rossi.bitfarm.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AcquistoController {
    IAcquistoService acquistoService;
    Sessione sessione;

    final String PATH = "acquisti";

    @Autowired
    public AcquistoController(IAcquistoService acquistoService) {
        this.acquistoService = acquistoService;
    }

    @RequestMapping(value = "/" + PATH + "/getArticoliCarrello", method = RequestMethod.GET)
    public ResponseEntity<Object> getArticoliCarrello() {
        var lista = this.acquistoService.listaArticoli();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/aggiungiAlCarrello/{quantita}", method = RequestMethod.POST)
    public ResponseEntity<Object> aggiungiAlCarrello(@Valid @RequestBody ProdottoDTO articolo, @Valid @PathVariable("quantita") int quantita) {
        IArticolo prodotto = new Prodotto(articolo.nome(), articolo.descrizione(), articolo.prezzo(), articolo.certificazioni());
        this.acquistoService.aggiungiAlCarrello(prodotto, quantita);
        return new ResponseEntity<>("Articolo aggiunto al Carrello correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/rimuoviDalCarrello", method = RequestMethod.DELETE)
    public ResponseEntity<Object> aggiungiAlCarrello(@Valid @RequestBody ProdottoDTO articolo) {
        IArticolo prodotto = new Prodotto(articolo.nome(), articolo.descrizione(), articolo.prezzo(), articolo.certificazioni());
        this.acquistoService.rimuoviDalCarrello(prodotto);
        return new ResponseEntity<>("Articolo rimosso dal Carrello correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/svuotaCarrello", method = RequestMethod.DELETE)
    public ResponseEntity<Object> svuotaCarrello() {
        this.acquistoService.svuotaCarrello();
        return new ResponseEntity<>("Carrello svuotato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/acquista", method = RequestMethod.POST)
    public ResponseEntity<Object> acquista(@Valid @RequestBody UtenteDTO utenteDTO, @Valid @RequestBody String pagamentoService) {
        if(sessione.isLogged()) {
            IPagamentoService servizio = new PagamentoService();
            Account account = sessione.getAccount();
            if(account instanceof Utente utente) {
                this.acquistoService.acquista(utente, servizio);
                return new ResponseEntity<>("Acquisto completato con successo", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Il tuo account non è abilitato per gli acquisti", HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>("Non è possibile effettuare acquisti se non si è effettuato prima il login", HttpStatus.UNAUTHORIZED);
    }
}
