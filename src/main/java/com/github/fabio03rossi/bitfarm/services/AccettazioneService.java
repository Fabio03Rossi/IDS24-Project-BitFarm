package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;
import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
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
        try {
            this.db.pubblicaArticolo(id);
        } catch (SQLException e){

        }
    }

    @Override
    public void accettaEvento(int id) {
        try {
            this.db.pubblicaEvento(id);
        } catch (SQLException e){

        }
    }

    @Override
    public void rifiutaArticolo(int id) {
        try {
            this.db.rifiutaArticolo(id);
        } catch (SQLException e){

        }
    }

    @Override
    public void rifiutaEvento(int id) {
        try {
            this.db.rifiutaEvento(id);
        } catch (SQLException e){

        }
    }
}
