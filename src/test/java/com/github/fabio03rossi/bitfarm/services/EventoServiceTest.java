package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.dto.EventoDTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class EventoServiceTest {
    EventoService eventoService = new EventoService();

    @Test
    void creaEvento() {
        var dto = new EventoDTO(
                "TestUtente",
                "Questa è una descrizione...",
                Date.from(Instant.now()),
                "TestIndirizzo n.20"
        );

        assertAll(
                () -> eventoService.creaEvento(
                        dto.nome(),
                        dto.descrizione(),
                        dto.data(),
                        dto.posizione()
                )
        );
    }

    @Test
    void eliminaEvento() {
        this.creaEvento();
        int id = 1;
        assertAll(
                () -> eventoService.eliminaEvento(id)
        );
    }

    @Test
    void modificaEvento() {
        this.creaEvento();
        var dto = new EventoDTO(
                "TestUtente",
                "Questa è una descrizione...",
                Date.from(Instant.now()),
                "TestIndirizzo n.20"
        );
        int id = 1;

        assertAll(
                () -> eventoService.modificaEvento(
                        id,
                        dto.nome(),
                        dto.descrizione(),
                        dto.data(),
                        dto.posizione()
                )
        );
    }
}