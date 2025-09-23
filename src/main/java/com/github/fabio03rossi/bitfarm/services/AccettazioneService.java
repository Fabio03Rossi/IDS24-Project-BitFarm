package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

public class AccettazioneService implements IAccettazioneService {
    private static final Logger logger = LoggerFactory.getLogger(AccettazioneService.class);
    private final DBManager db;

    public AccettazioneService() {
        db = DBManager.getInstance();
    }

    @Override
    public List<Contenuto> getAllRichieste() {
        List<? extends Contenuto> articoli = List.of();
        List<? extends Contenuto> eventi = List.of();

        return Stream.concat(articoli.stream(), eventi.stream()).toList();
    }

    @Override
    public void accettaArticolo(int id) {
        this.db.pubblicaArticolo(id);
    }

    @Override
    public void accettaEvento(int id) {
        this.db.pubblicaEvento(id);
    }

    @Override
    public void rifiutaArticolo(int id) {
        this.db.rifiutaArticolo(id);
    }

    @Override
    public void rifiutaEvento(int id) {
        this.db.rifiutaEvento(id);
    }
}
