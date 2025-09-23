package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.dto.AziendaDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

class VerificaServiceTest {
    VerificaService verificaService = new VerificaService();

    @Test
    void getAllRichieste() {
        assertAll(
                () -> verificaService.getAllRichieste()
        );
    }

    @Test
    void accettaRegistrazioneAzienda() {
        int id = 1;

        assertAll(
                () -> verificaService.accettaRegistrazioneAzienda(id)
        );
    }

    @Test
    void rifiutaRegistrazioneAzienda() {
        int id = 1;

        assertAll(
                () -> verificaService.rifiutaRegistrazioneAzienda(id)
        );
    }

    private void creaAzienda() {
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

        new AccountService().registraAzienda(
                dto.partitaIVA(),
                dto.nome(),
                dto.email(),
                dto.password(),
                dto.descrizione(),
                dto.indirizzo(),
                dto.telefono(),
                dto.tipologia(),
                dto.certificazioni()
        );
    }
}