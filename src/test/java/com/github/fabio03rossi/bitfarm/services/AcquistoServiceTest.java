package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.acquisto.Carrello;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.dto.ProdottoDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AcquistoServiceTest {
    AcquistoService acquistoService = new AcquistoService();

    @Test
    void aggiungiAlCarrello() {
        var prodotto = new Prodotto(
                "TestUtente",
                "Questa è una descrizione...",
                Double.parseDouble("10.0"),
                "Certificazioni"
        );
        prodotto.setId(1);

        assertAll(
                () -> acquistoService.aggiungiAlCarrello(prodotto, 3)
        );
    }

    @Test
    void rimuoviDalCarrello() {
        this.aggiungiAlCarrello();
        var prodotto = new Prodotto(
                "TestUtente",
                "Questa è una descrizione...",
                Double.parseDouble("10.0"),
                "Certificazioni"
        );
        prodotto.setId(1);

        assertAll(
                () -> acquistoService.rimuoviDalCarrello(prodotto)
        );
    }

    @Test
    void svuotaCarrello() {
        this.aggiungiAlCarrello();

        assertAll(
                () -> acquistoService.svuotaCarrello()
        );
    }

    @Test
    void acquista() {
        var utente = new Utente(
                "TestUtente",
                "testutente@gmail.com",
                "testpassword",
                "TestIndirizzo"
        );

        var pagamento = new IPagamentoService() {
            @Override
            public boolean buy(Carrello carrello) {
                return IPagamentoService.super.buy(carrello);
            }

            @Override
            public String getNome() {
                return "Carta di Credito";
            }
        };

        assertAll(
                () -> acquistoService.acquista(utente, pagamento)
        );
    }
}