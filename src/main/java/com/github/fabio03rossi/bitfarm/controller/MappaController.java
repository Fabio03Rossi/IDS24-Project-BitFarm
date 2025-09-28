package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.services.IMappaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MappaController {
    IMappaService mappaService;

    final String PATH = "mappa";

    @Autowired
    public MappaController(IMappaService mappaService) {
        this.mappaService = mappaService;
    }

    @RequestMapping(value = "/" + PATH + "/svuotaCarrello", method = RequestMethod.GET)
    public ResponseEntity<Object> getAllIndirizzi() {
        var indirizzi = this.mappaService.getAllIndirizzi();
        return new ResponseEntity<>(indirizzi, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getIndirizzoAzienda/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getIndirizzoAzienda(@Valid @PathVariable("id") int id) {
        var indirizzi = this.mappaService.getIndirizzoAzienda(id);
        return new ResponseEntity<>(indirizzi, HttpStatus.OK);
    }

    @RequestMapping(value = "/" + PATH + "/getIndirizzoEvento/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getIndirizzoEvento(@Valid @PathVariable("id") int id) {
        var indirizzi = this.mappaService.getIndirizzoEvento(id);
        return new ResponseEntity<>(indirizzi, HttpStatus.OK);
    }
}
