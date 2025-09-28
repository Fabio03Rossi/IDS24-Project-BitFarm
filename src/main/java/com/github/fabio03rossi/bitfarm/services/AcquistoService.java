package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.acquisto.Carrello;
import com.github.fabio03rossi.bitfarm.acquisto.Ordine;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import com.github.fabio03rossi.bitfarm.exception.CarrelloVuotoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AcquistoService implements IAcquistoService {
    private static final Logger log = LoggerFactory.getLogger(AcquistoService.class);
    private Carrello carrello;
    private IPagamentoService pagamentoService;
    private final DBManager db;

    public AcquistoService() {
        this.db = DBManager.getInstance();
    }

    @Override
    public Map<IArticolo, Integer> listaArticoli() {
        return this.carrello == null ?  Map.of() : this.carrello.getListaArticolo();
    }

    @Override
    public void aggiungiAlCarrello(IArticolo articolo, int quantita) {
        if(this.carrello == null) {
            this.carrello = new Carrello();
        }
        this.carrello.addArticolo(articolo, quantita);
    }

    @Override
    public void rimuoviDalCarrello(IArticolo articolo) {
        if(this.carrello == null) throw new CarrelloVuotoException("Il carrello non può essere vuoto.");

        try{
            this.carrello.rimuoviArticolo(articolo);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public void svuotaCarrello() {
        this.carrello = null;
    }

    @Override
    public void acquista(Utente utente, IPagamentoService pagamentoService) {
        // Tenta l'acquisto
        if(this.carrello == null) throw new CarrelloVuotoException("Il carrello non può essere vuoto.");

        if(pagamentoService.buy(carrello)){
            // Se il pagamento è andato a buon fine creo l'ordine e aggiorno il database
            try {
                String metodoPagamento = pagamentoService.getNome();
                Ordine ordine = new Ordine(utente.getIndirizzo(), utente.getId(), carrello, metodoPagamento);
                this.db.addOrdine(ordine);

            }catch (Exception e) {
                log.error("AcquistoService: Errore durante l'acquisto");
                throw e;
            }
        }
    }
}
