package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    AccountService accountService = new AccountService();

    @Test
    void registraAccount() {
        var dto = new UtenteDTO(
                "TestUtente",
                "testutente@gmail.com",
                "testpassword",
                "Test Indirizzo n.26"
                );

        assertAll(
                () -> accountService.registraAccount(dto.nickname(), dto.email(), dto.password(), dto.indirizzo())
        );
    }

    @Test
    void loginAccount() {
    }

    @Test
    void eliminaUtente() {
        this.registraAccount();
        int id = 1;

        assertAll(
                () -> accountService.eliminaUtente(id)
        );
    }

    @Test
    void modificaAccount() {
        var dto = new UtenteDTO(
                "TestUtente",
                "testutente@gmail.com",
                "testpassword",
                "Test Indirizzo n.26"
        );
        int id = 1;

        assertAll(
                () -> accountService.modificaAccount(id, dto.nickname(), dto.email(), dto.password(), dto.indirizzo())
        );
    }

    @Test
    void registraAzienda() {
        var dto = new AziendaDTO(
                "TestPartitaIVA",
                "TestUtente",
                "testutente@gmail.com",
                "testpassword",
                "Questa è una descrizione",
                "Test Indirizzo n.26",
                "00000000",
                "Trasformatore",
                "Varie Certificazioni qui..."
        );

        assertAll(
                () -> accountService.registraAzienda(
                        dto.partitaIVA(),
                        dto.nome(),
                        dto.email(),
                        dto.password(),
                        dto.descrizione(),
                        dto.indirizzo(),
                        dto.telefono(),
                        dto.tipologia(),
                        dto.certificazioni()
                )
        );
    }

    @Test
    void loginAzienda() {
    }

    @Test
    void eliminaAzienda() {
        this.registraAzienda();
        int id = 1;

        assertAll(
                () -> accountService.eliminaAzienda(id)
        );
    }

    @Test
    void modificaAzienda() {
        this.registraAzienda();

        var dto = new AziendaDTO(
                "TestPartitaIVA",
                "TestUtente",
                "testutente@gmail.com",
                "testpassword",
                "Questa è una descrizione",
                "Test Indirizzo n.26",
                "00000000",
                "Trasformatore",
                "Varie Certificazioni qui..."
        );
        int id = 1;

        assertAll(
                () -> accountService.modificaAzienda(
                        id,
                        dto.partitaIVA(),
                        dto.nome(),
                        dto.email(),
                        dto.password(),
                        dto.descrizione(),
                        dto.indirizzo(),
                        dto.telefono(),
                        dto.tipologia(),
                        dto.certificazioni()
                )
        );
    }
}