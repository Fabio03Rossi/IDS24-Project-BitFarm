package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Azienda;
import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MappaService implements IMappaService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MappaService.class);
    private final DBManager db = DBManager.getInstance();

    @Override
    public List<String> getAllIndirizzi() {
        List<String> indirizzi = new ArrayList<>(db.getAllAziende()
                .stream()
                .map(Azienda::getIndirizzo)
                .toList()
        );
        indirizzi.addAll(db.getAllEventiAccettati()
                .stream()
                .map(Evento::getPosizione)
                .toList()
        );
        return indirizzi;
    }

    @Override
    public String getIndirizzoAzienda(int id) {
        return db.getAzienda(id).getIndirizzo();
    }

    @Override
    public String getIndirizzoEvento(int id) {
        return db.getEvento(id).getPosizione();
    }
}
