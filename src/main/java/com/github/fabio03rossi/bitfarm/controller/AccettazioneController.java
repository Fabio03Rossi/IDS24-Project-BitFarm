package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.exception.DatiNonTrovatiException;
import com.github.fabio03rossi.bitfarm.services.IAccettazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccettazioneController {
    IAccettazioneService accettazioneService;

    @Autowired
    public AccettazioneController(IAccettazioneService accettazioneService) {
        this.accettazioneService = accettazioneService;
    }

    @RequestMapping(value = "/moderazione/getAllRichiesteAccettazioni", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllRichiesteAccettazioni() {
        var richieste = this.accettazioneService.getAllRichieste();
        return new ResponseEntity<>(richieste, HttpStatus.OK);
    }

    @RequestMapping(value = "/moderazione/accettaArticolo/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> accettaArticolo(@PathVariable("id") int id) {
        this.accettazioneService.accettaArticolo(id);
        return new ResponseEntity<>("Articolo accettato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/moderazione/accettaEvento/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> accettaEvento(@PathVariable("id") int id) {
        this.accettazioneService.accettaEvento(id);
        return new ResponseEntity<>("Evento accettato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/moderazione/rifiutaArticolo/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> rifiutaArticolo(@PathVariable("id") int id) {
        this.accettazioneService.rifiutaArticolo(id);
        return new ResponseEntity<>("Evento accettato correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/moderazione/rifiutaEvento/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> rifiutaEvento(@PathVariable("id") int id) {
        this.accettazioneService.rifiutaEvento(id);
        return new ResponseEntity<>("Evento accettato correttamente.", HttpStatus.OK);
    }

    @ExceptionHandler(value = DatiNonTrovatiException.class)
    public ResponseEntity<Object> datiNonTrovatiException(DatiNonTrovatiException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
