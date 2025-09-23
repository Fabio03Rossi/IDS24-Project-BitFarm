package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.services.IAccettazioneService;
import com.github.fabio03rossi.bitfarm.services.IVerificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificaController {
    IVerificaService verificaService;

    @Autowired
    public VerificaController(IVerificaService verificaService) {
        this.verificaService = verificaService;
    }
}
