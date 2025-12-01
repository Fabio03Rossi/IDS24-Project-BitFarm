package com.github.fabio03rossi.bitfarm;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.account.Curatore;
import com.github.fabio03rossi.bitfarm.account.GestoreDellaPiattaforma;
import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Pacchetto;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.services.AccountService;
import com.github.fabio03rossi.bitfarm.services.ArticoloService;
import org.springframework.security.core.parameters.P;

public class Example {

    public void popolaDB() {
        ArticoloService articoloService = new ArticoloService();
        AccountService accountService = new AccountService();

        Prodotto prodotto1 = new Prodotto("miele", "è giallo", 6.50, "BIO");
        articoloService.creaArticolo(prodotto1.toDTO());
        Prodotto prodotto2 = new Prodotto("cioccolata", "è marrone", 3.34, "ChiccoPremium");
        articoloService.creaArticolo(prodotto2.toDTO());
        Pacchetto pacchetto = new Pacchetto("pacchetto1", "cioccolata e miele scontati", 9.11, "BIO, ChiccoPremium");
        pacchetto.addProduct(prodotto1, 1);
        pacchetto.addProduct(prodotto2, 1);
        articoloService.creaPacchetto(pacchetto.toDTO());

        Azienda azienda = new Azienda("GB123 4567 89", "PalmaRant", "PalmaRant@gmail.com", "1234", "Il latte che si lamenta quando viene munto.", "via delle mucche 12", "394122334", "Latticini", "MUUU");
        accountService.registraAzienda(azienda.toDTO());
        Curatore curatore = new Curatore("curatore@gmail.com", "2345", "Giorgiovanni", "Via dei curatori 34");
        accountService.registraCuratore(curatore.toDTO());
        Utente utente = new Utente("Fabio", "FabioRossi@gmail.com", "4321", "via dei fabi 76");
        accountService.registraUtente(utente.toDTO());
        GestoreDellaPiattaforma gestoreDellaPiattaforma = new GestoreDellaPiattaforma("GrandePuffo@gmail.com", "9876", "Grande Puffo", "via dei puffi 17");
        accountService.registraGestoreDellaPiattaforma(gestoreDellaPiattaforma.toDTO());

    }

}
