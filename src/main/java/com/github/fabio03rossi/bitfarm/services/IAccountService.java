package com.github.fabio03rossi.bitfarm.services;

public interface IAccountService {
    void registraAccount(int id, String nickname, String email, String password);
    void loginAccount();
    void eliminaAccount(int id);
    void modificaAccount(int id, String nickname, String email, String password);

    void registraAzienda();
    void loginAzienda();
    void eliminaAzienda();
    void modificaAzienda();
}
