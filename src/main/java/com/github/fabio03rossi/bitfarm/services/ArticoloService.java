package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Pacchetto;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.Prodotto;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import com.github.fabio03rossi.bitfarm.dto.PacchettoDTO;
import com.github.fabio03rossi.bitfarm.dto.ProdottoDTO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticoloService implements IArticoloService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ArticoloService.class);
    private final DBManager db = DBManager.getInstance();

    @Override
    public void creaArticolo(ProdottoDTO dto) {
        var articolo = new Prodotto(dto.nome(), dto.descrizione(), dto.prezzo(), dto.certificazioni());
        articolo.setStato(new StatoValidazione());

        this.db.addArticolo(articolo);
    }

    @Override
    public void creaPacchetto(PacchettoDTO dto) {
        var pacchetto = new Pacchetto(dto.nome(), dto.descrizione(), dto.prezzo(), dto.certificazioni());
        pacchetto.setStato(new StatoValidazione());

        this.db.addArticolo(pacchetto);
    }

    @Override
    public void eliminaArticolo(int id) {
        this.db.cancellaArticolo(id);
    }

    @Override
    public void modificaArticolo(ProdottoDTO dto, int id) {
        try {
            IArticolo articolo = new Prodotto(dto.nome(), dto.descrizione(), dto.prezzo(), dto.certificazioni());
            articolo.setId(id);
            this.db.updateArticolo(articolo);
        } catch (Exception ex) {
            log.warn("C'è stato un problema nell'aggiornamento dell'articolo.");
            throw ex;
        }
    }

    @Override
    public void modificaArticolo(PacchettoDTO dto, int id) {
        try {
            IArticolo articolo = new Pacchetto(dto.nome(), dto.descrizione(), dto.prezzo(), dto.certificazioni());
            articolo.setId(id);
            this.db.updateArticolo(articolo);
        } catch (Exception ex) {
            log.warn("C'è stato un problema nell'aggiornamento del pacchetto.");
            throw ex;
        }
    }

    @Override
    public void eliminaPacchetto(int id) {
        this.db.cancellaArticolo(id);
    }

    @Override
    public IArticolo getArticolo(int id) {
        IArticolo articolo = this.db.getArticolo(id);
        articolo.setId(id);
        return articolo;

    }

    @Override
    public List<IArticolo> getAllArticoli() {
        List<IArticolo> lista = db.getAllArticoli();
        return lista;
    }
}
