package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.dto.EventoDTO;
import com.github.fabio03rossi.bitfarm.dto.PacchettoDTO;
import com.github.fabio03rossi.bitfarm.dto.ProdottoDTO;
import com.github.fabio03rossi.bitfarm.services.IArticoloService;
import com.github.fabio03rossi.bitfarm.services.IEventoService;
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
public class ContenutiController {
    IArticoloService articoloService;
    IEventoService eventoService;

    final String PATH = "contenuti";

    @Autowired
    public ContenutiController(IArticoloService articoloService, IEventoService eventoService) {
        this.articoloService = articoloService;
        this.eventoService = eventoService;
    }

    @RequestMapping(value = "/" + PATH + "/creaArticolo", method = RequestMethod.POST)
    public ResponseEntity<Object> creaArticolo(@Valid @RequestBody ProdottoDTO prodotto) {
        this.articoloService.creaArticolo(prodotto);
        return new ResponseEntity<>("Articolo creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/" + PATH + "/creaPacchetto", method = RequestMethod.POST)
    public ResponseEntity<Object> creaPacchetto(@Valid @RequestBody PacchettoDTO prodotto) {
        this.articoloService.creaPacchetto(prodotto);
        return new ResponseEntity<>("Pacchetto creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/" + PATH + "/modificaArticolo/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> modificaArticolo(@PathVariable("id") int id, @Valid @RequestBody ProdottoDTO prodotto) {
        this.articoloService.modificaArticolo(prodotto, id);
        return new ResponseEntity<>("Articolo modificato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/modificaPacchetto/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> modificaPacchetto(@PathVariable("id") int id, @Valid @RequestBody PacchettoDTO pacchetto) {
        this.articoloService.modificaArticolo(pacchetto, id);
        return new ResponseEntity<>("Pacchetto modificato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/eliminaArticolo/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaArticolo(@PathVariable("id") int id) {
        this.articoloService.eliminaArticolo(id);
        return new ResponseEntity<>("Articolo eliminato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/eliminaPacchetto/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaPacchetto(@PathVariable("id") int id) {
        this.articoloService.eliminaArticolo(id);
        return new ResponseEntity<>("Pacchetto eliminato correttamente.", HttpStatus.OK);
    }


    @RequestMapping(value = "/" + PATH + "/creaEvento", method = RequestMethod.POST)
    public ResponseEntity<Object> creaEvento(@Valid @RequestBody EventoDTO evento) {
        this.eventoService.creaEvento(evento);
        return new ResponseEntity<>("Evento creato correttamente.", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/" + PATH + "/modificaEvento/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> modificaEvento(@PathVariable("id") int id, @Valid @RequestBody EventoDTO evento) {
        this.eventoService.modificaEvento(id, evento);
        return new ResponseEntity<>("Evento modificato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/eliminaEvento/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> eliminaEvento(@PathVariable("id") int id) {
        this.eventoService.eliminaEvento(id);
        return new ResponseEntity<>("Evento eliminato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getArticolo/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getArticolo(@PathVariable("id") int id) {
        var articolo = this.articoloService.getArticolo(id);
        return new ResponseEntity<>(articolo, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getAllArticoli", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllArticoli() {
        var articolo = this.articoloService.getAllArticoli();
        return new ResponseEntity<>(articolo, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getAllEventi", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllEventi() {
        var evento = this.eventoService.getAllEventi();
        return new ResponseEntity<>(evento, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getArticoliAccettati", method = RequestMethod.GET)
    public ResponseEntity<Object> getArticoliAccettati() {
        var articolo = this.articoloService.getArticoliAccettati();
        return new ResponseEntity<>(articolo, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getEventiAccettati", method = RequestMethod.GET)
    public ResponseEntity<Object> getEventiAccettati() {
        var evento = this.eventoService.getEventiAccettati();
        return new ResponseEntity<>(evento, HttpStatus.OK);
    }
}
