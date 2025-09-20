package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.RichiestaAccettazione;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.AbstractArticolo;
import com.github.fabio03rossi.bitfarm.contenuto.articolo.IArticolo;
import com.github.fabio03rossi.bitfarm.database.DBSingleton;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccettazioneService implements IAccettazioneService {
    private static final Logger LOGGER = Logger.getLogger( AccettazioneService.class.getName() );

    @Override
    public List<RichiestaAccettazione> getAllRichieste() {
        List<RichiestaAccettazione> result = new ArrayList<>();

        List<AbstractArticolo> articoli = List.of();
        List<Evento> eventi = List.of();

        articoli.forEach(element -> result.add(new RichiestaAccettazione(element)));
        eventi.forEach(element -> result.add(new RichiestaAccettazione(element)));

        return result;
    }

    @Override
    public void accettaContenuto(RichiestaAccettazione r) {
        try{
            handleContenuto(r, true);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "C'è stato un errore nell'accettazione del contenuto.", e);
            System.exit(1);
        }
    }

    @Override
    public void rifiutaContenuto(RichiestaAccettazione r) {
        try{
            handleContenuto(r, false);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "C'è stato un errore nel rifiuto del contenuto.", e);
            System.exit(1);
        }
    }

    private void handleContenuto(RichiestaAccettazione r, boolean b) throws SQLException {
        var db = DBSingleton.getInstance();

        if(r.contenuto() instanceof IArticolo articolo) {
            if (b) db.pubblicaArticolo(articolo);
            else db.cancellaArticolo(articolo);

        } else if(r.contenuto() instanceof Evento evento)
            if (b) db.pubblicaEvento(evento);
            else db.cancellaEvento(evento);
    }
}
