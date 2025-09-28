package com.github.fabio03rossi.bitfarm.services;

import java.util.List;

public interface IMappaService {
    List<String> getAllIndirizzi();

    String getIndirizzoAzienda(int id);

    String getIndirizzoEvento(int id);
}
