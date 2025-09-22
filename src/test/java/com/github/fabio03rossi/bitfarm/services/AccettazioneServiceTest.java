package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class AccettazioneServiceTest {
    AccettazioneService accettazioneService = new AccettazioneService();

    @Test
    void getAllRichieste() {
        assertAll(
                () -> accettazioneService.getAllRichieste()
        );
    }

    @Test
    void accettaArticolo() {
        int id = 1;

        assertAll(
                () -> accettazioneService.accettaArticolo(id)
        );
    }

    @Test
    void accettaEvento() {
        int id = 1;

        assertAll(
                () -> accettazioneService.accettaEvento(id)
        );
    }

    @Test
    void rifiutaArticolo() {
        int id = 1;

        assertAll(
                () -> accettazioneService.rifiutaArticolo(id)
        );
    }

    @Test
    void rifiutaEvento() {
        int id = 1;

        assertAll(
                () -> accettazioneService.rifiutaEvento(id)
        );
    }
}