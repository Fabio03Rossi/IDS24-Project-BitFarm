package com.github.fabio03rossi.bitfarm.account;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;


public abstract class Account {
    private Date dataCreazione;
    private String email;
    private String password;

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
}
