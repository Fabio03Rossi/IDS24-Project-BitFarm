package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.database.DBManager;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappaService implements IMappaService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MappaService.class);
    private final DBManager db = DBManager.getInstance();

    //TODO: Ottenere le posizioni
    @Override
    public List<String> getAllIndirizzi() {
        List<String> indirizzi = List.of();

        return indirizzi;
    }

    //TODO: funzione di ricerca
    @Override
    public String getIndirizzoAzienda(int id) {
        return "";
    }

    @Override
    public String getIndirizzoEvento(int id) {
        return "";
    }
}
