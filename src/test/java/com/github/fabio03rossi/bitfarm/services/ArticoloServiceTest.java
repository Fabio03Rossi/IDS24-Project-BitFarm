package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.dto.PacchettoDTO;
import com.github.fabio03rossi.bitfarm.dto.ProdottoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

class ArticoloServiceTest {
    ArticoloService articoloService = new ArticoloService();

    @Test
    void creaArticolo() {
        var dto = new ProdottoDTO(
                "TestUtente",
                "Questa è una descrizione...",
                Double.parseDouble("10.0"),
                "Certificazioni"
        );

        assertAll(
                () -> articoloService.creaArticolo(
                        dto.nome(),
                        dto.descrizione(),
                        dto.prezzo(),
                        dto.certificazioni()
                )
        );
    }

    @Test
    void creaPacchetto() {
        var dto = new PacchettoDTO(
                "TestUtente",
                "Questa è una descrizione...",
                Double.parseDouble("10.0"),
                "Certificazioni"
        );

        assertAll(
                () -> articoloService.creaPacchetto(
                        dto.nome(),
                        dto.descrizione(),
                        dto.prezzo(),
                        dto.certificazioni()
                )
        );
    }

    @Test
    void eliminaArticolo() {
        this.creaArticolo();
        int id = 1;
        assertAll(
                () -> articoloService.eliminaArticolo(id)
        );
    }

    @Test
    void modificaArticolo() {
        this.creaArticolo();

        var dto = new ProdottoDTO(
                "TestUtente",
                "Questa è una descrizione...",
                Double.parseDouble("10.0"),
                "Certificazioni"
        );
        int id = 1;

        assertAll(
                () -> articoloService.modificaArticolo(dto, id)
        );
    }

    @Test
    void eliminaPacchetto() {
        this.creaPacchetto();
        int id = 1;
        assertAll(
                () -> articoloService.eliminaPacchetto(id)
        );
    }

    @Test
    void modificaPacchetto() {
        this.creaPacchetto();

        var dto = new PacchettoDTO(
                "TestUtente",
                "Questa è una descrizione...",
                Double.parseDouble("10.0"),
                "Certificazioni"
        );
        int id = 1;

        assertAll(
                () -> articoloService.modificaPacchetto(
                        id,
                        dto.nome(),
                        dto.descrizione(),
                        dto.prezzo(),
                        dto.certificazioni()
                )
        );
    }
}