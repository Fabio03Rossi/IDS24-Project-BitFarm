package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.acquisto.Carrello;
import com.github.fabio03rossi.bitfarm.acquisto.Ordine;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.database.DBManager;


public class AcquistoService implements IAcquistoService {
    private Carrello carrello;
    private IPagamentoService pagamentoService;
    private DBManager db;

    public AcquistoService(IPagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
        this.db = DBManager.getInstance();
    }

    @Override
    public void aggiungiAlCarrello(IArticolo articolo, int quantita) {
        this.carrello.addArticolo(articolo, quantita);
    }

    @Override
    public void rimuoviDalCarrello(IArticolo articolo, int quantita) {
        this.carrello.rimuoviArticolo(articolo);
    }

    @Override
    public void svuotaCarrello() {
        this.carrello.svuotaCarrello();
    }

    @Override
    public void acquista(Utente utente) {
        if(pagamentoService.buy(carrello)){
            // Se il pagamento è andato a buon fine creo l'ordine e aggiorno il database
            try {
                String metodoPagamento = pagamentoService.getNome();
                Ordine ordine = new Ordine(utente.getIndirizzo(), utente.getId(), carrello, metodoPagamento);

                //TODO db.addOrdine();

            }catch (Exception e) {}
        }
    }
}
