package com.github.fabio03rossi.bitfarm.misc;
import com.github.fabio03rossi.bitfarm.account.Account;
import com.github.fabio03rossi.bitfarm.acquisto.Carrello;
import com.github.fabio03rossi.bitfarm.database.DBManager;
import com.github.fabio03rossi.bitfarm.services.IAccountService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("Session")
public class Sessione {
    /**
     * La classe Session si occupa di contenere i dati della sessione corrente
     */
    private Account accountCorrente;
    private boolean loggedIn;
    private IAccountService accountService;
    private Carrello carrello;
    private static Sessione instance;

    private Sessione(){}

    public static Sessione getInstance() {
        if (instance == null) {
            instance = new Sessione();
        }
        return instance;
    }

    public void login(Account account) {
        /**
         * Effettua il login tramite IAccountService
         * @return true se il login è stato effettuato con successo, false se i dati inseriti non sono corretti
         */
        this.loggedIn = true;
        this.accountCorrente = account;
    }

    public boolean isLogged(){
        return this.loggedIn;
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
