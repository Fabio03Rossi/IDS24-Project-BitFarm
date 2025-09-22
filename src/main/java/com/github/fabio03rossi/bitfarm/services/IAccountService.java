package com.github.fabio03rossi.bitfarm.services;

public interface IAccountService {
    void registraAccount(String nickname, String email, String password);
    boolean loginAccount(String email, String password);
    void eliminaAccount(int id);
    void modificaAccount(int id, String nickname, String email, String password);

    void registraAzienda(String partitaIVA, String nome, String email, String password, String descrizione, String indirizzo, String telefono, String tipologia, String certificazioni);
    void loginAzienda(String nome, String password);
    void eliminaAzienda(int id);
    void modificaAzienda(int id, String nome, String email, String password, String descrizione, String indirizzo, String telefono, String tipologia, String certificazioni);
}
