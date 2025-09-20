package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;
import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.database.DBManager;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class AccettazioneService implements IAccettazioneService {
    private static final Logger LOGGER = Logger.getLogger(AccettazioneService.class.getName());

    @Override
    public List<Contenuto> getAllRichieste() {
        List<? extends Contenuto> articoli = List.of();
        List<? extends Contenuto> eventi = List.of();

        return Stream.concat(articoli.stream(), eventi.stream()).toList();
    }

    @Override
    public void accettaContenuto(Contenuto r) {
        try{
            handleContenuto(r, true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "C'è stato un errore nell'accettazione del contenuto.", e);
            System.exit(1);
        }
    }

    @Override
    public void rifiutaContenuto(Contenuto r) {
        try{
            handleContenuto(r, false);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "C'è stato un errore nel rifiuto del contenuto.", e);
            System.exit(1);
        }
    }

    private void handleContenuto(Contenuto r, boolean b) throws SQLException {
        var db = DBManager.getInstance();

        if(r instanceof IArticolo articolo) {
            if (b) db.pubblicaArticolo(articolo);
            else db.cancellaArticolo(articolo);

        } else if(r instanceof Evento evento)
            if (b) db.pubblicaEvento(evento);
            else db.cancellaEvento(evento);
    }
}
