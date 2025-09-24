package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.services.IVerificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificaController {
    IVerificaService verificaService;

    @Autowired
    public VerificaController(IVerificaService verificaService) {
        this.verificaService = verificaService;
    }

    @RequestMapping(value = "/verifiche/getAllRichiesteAziende", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllRichiesteAziende() {
        var richieste = this.verificaService.getAllRichieste();
        return new ResponseEntity<>(richieste, HttpStatus.OK);
    }

    @RequestMapping(value = "/verifiche/accettaRegistrazioneAzienda/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> accettaRegistrazioneAzienda(@PathVariable("id") int id) {
        this.verificaService.accettaRegistrazioneAzienda(id);
        return new ResponseEntity<>("Azienda accettata correttamente.", HttpStatus.OK);
    }

    @RequestMapping(value = "/verifiche/rifiutaRegistrazioneAzienda/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> rifiutaRegistrazioneAzienda(@PathVariable("id") int id) {
        this.verificaService.rifiutaRegistrazioneAzienda(id);
        return new ResponseEntity<>("Azienda rifiutata correttamente.", HttpStatus.OK);
    }

}
