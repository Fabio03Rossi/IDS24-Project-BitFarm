package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.services.IAccettazioneService;
import com.github.fabio03rossi.bitfarm.services.IArticoloService;
import com.github.fabio03rossi.bitfarm.services.IEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContenutiController {
    IArticoloService articoloService;
    IEventoService eventoService;

    @Autowired
    public ContenutiController(IArticoloService articoloService, IEventoService eventoService) {
        this.articoloService = articoloService;
        this.eventoService = eventoService;
    }
}
