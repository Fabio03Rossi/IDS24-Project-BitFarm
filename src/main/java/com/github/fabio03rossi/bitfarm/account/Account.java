package com.github.fabio03rossi.bitfarm.account;

import java.time.Instant;
import java.util.Date;


public abstract class Account {
    private final Date dataCreazione;
    private String email;
    private String password;
    private int id = -1;

    public Account(String email, String password) {
        this.dataCreazione = Date.from(Instant.now());
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Date getDataCreazione() {
        return this.dataCreazione;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }
}
