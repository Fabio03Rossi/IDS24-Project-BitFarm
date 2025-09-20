package com.github.fabio03rossi.bitfarm.services;

import com.github.fabio03rossi.bitfarm.account.Utente;
import com.github.fabio03rossi.bitfarm.contenuto.Evento;
import com.github.fabio03rossi.bitfarm.contenuto.StatoValidazione;
import com.github.fabio03rossi.bitfarm.database.DBManager;

public class AccountService implements IAccountService {
    private final DBManager db = DBManager.getInstance();

    @Override
    public void registraAccount(int id, String nickname, String email, String password) {
        try {
            var utente = new Utente(id, nickname, email, password);

            this.db.setUtente(utente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginAccount() {

    }

    @Override
    public void eliminaAccount(int id) {

    }

    @Override
    public void modificaAccount(int id, String nickname, String email, String password) {

    }

    @Override
    public void registraAzienda() {

    }

    @Override
    public void loginAzienda() {

    }

    @Override
    public void eliminaAzienda() {

    }

    @Override
    public void modificaAzienda() {

    }
}
