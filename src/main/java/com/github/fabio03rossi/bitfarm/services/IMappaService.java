package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.contenuto.Contenuto;

import java.util.List;

public interface IMappaService {
    List<String> getAllIndirizziAziende();

    List<String> getAllIndirizziEventi();

    String getIndirizzoAzienda(int id);

    String getIndirizzoEvento(int id);
}
