package com.github.fabio03rossi.bitfarm.misc;
import com.github.fabio03rossi.bitfarm.account.Account;
import com.github.fabio03rossi.bitfarm.acquisto.Carrello;
import com.github.fabio03rossi.bitfarm.services.IAccountService;


public class Sessione {
    /**
     * La classe Session si occupa di contenere i dati della sessione corrente
     */
    private Account accountCorrente;
    private boolean loggedIn;
    private IAccountService accountService;
    private Carrello carrello;

    public Sessione(Account account) {
        this.accountCorrente = account;
        this.loggedIn = false;
    }

    public boolean login() {
        /**
         * Effettua il login tramite IAccountService
         * @return true se il login è stato effettuato con successo, false se i dati inseriti non sono corretti
         */
        boolean result = accountService.loginAccount(this.accountCorrente.getEmail(), this.accountCorrente.getPassword());
        if (result) {
            this.loggedIn = true;
            return true;
        }
        System.out.println("Session: login fallito");
        return false;
    }

    public void logout() {
        /**
         * Effettua il logout dell'account
         */
        this.loggedIn = false;
        this.accountCorrente = null;
    }

    public Account getAccount() {
        /**
         * getter dell'Account
         * @return Account accountCorrente
         */
        if (this.accountCorrente == null) {
            System.out.println("Session: Devi ancora effettuare il login");
            return null;
        }
        return this.accountCorrente;
    }

}
