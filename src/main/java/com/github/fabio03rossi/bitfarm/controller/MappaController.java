package com.github.fabio03rossi.bitfarm.controller;

import com.github.fabio03rossi.bitfarm.services.IMappaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MappaController {
    IMappaService mappaService;

    @Autowired
    public MappaController(IMappaService mappaService) {
        this.mappaService = mappaService;
    }
}
