package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.services.IAccettazioneService;
import com.github.fabio03rossi.bitfarm.services.IAcquistoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccettazioneController {
    IAccettazioneService accettazioneService;

    @Autowired
    public AccettazioneController(IAccettazioneService accettazioneService) {
        this.accettazioneService = accettazioneService;
    }
}
