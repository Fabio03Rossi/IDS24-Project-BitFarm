package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.services.IOrdiniService;
import com.github.fabio03rossi.bitfarm.services.IVerificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdiniController {
    IOrdiniService ordiniService;

    final String PATH = "ordini";

    @Autowired
    public OrdiniController(IOrdiniService ordiniService) {
        this.ordiniService = ordiniService;
    }

    @RequestMapping(value = "/" + PATH + "/getOrdine/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrdine(@PathVariable("id") int id) {
        var ordine = this.ordiniService.getOrdine(id);
        return new ResponseEntity<>(ordine, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/cancellaOrdine/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> cancellaOrdine(@PathVariable("id") int id) {
        this.ordiniService.cancellaOrdine(id);
        return new ResponseEntity<>("Ordine cancellato correttamente.", HttpStatus.OK);
    }
}
