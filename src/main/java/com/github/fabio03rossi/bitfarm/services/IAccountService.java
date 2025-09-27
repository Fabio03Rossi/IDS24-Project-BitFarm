package com.github.fabio03rossi.bitfarm.services;

public interface IAccountService {
    void registraUtente(String nickname, String email, String password, String indirizzo);

    void registraGestoreDellaPiattaforma(String email, String password, String nickname, String indirizzo);

    void registraCuratore(String email, String password);

    void registraAzienda(String partitaIVA, String nome, String email, String password, String descrizione, String indirizzo, String telefono, String tipologia, String certificazioni);

    boolean modificaAzienda(int id, String partitaIVA, String nome, String email, String password, String descrizione, String indirizzo, String telefono, String tipologia, String certificazioni);

    boolean modificaUtente(int id, String nickname, String email, String password, String indirizzo);

    boolean modificaCuratore(int id, String email, String password);

    boolean modificaGestoreDellaPiattaforma(int id, String email, String password, String indirizzo);

    boolean eliminaUtente(int id);

    boolean eliminaAzienda(int id);

    boolean eliminaCuratore(int id);

    boolean eliminaGestoreDellaPiattaforma(int id);

    boolean loginAccount(String email, String password);

    boolean loginAzienda(String nome, String password);
}
