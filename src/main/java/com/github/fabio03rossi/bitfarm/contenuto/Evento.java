package com.github.fabio03rossi.bitfarm.contenuto;

import com.github.fabio03rossi.bitfarm.misc.Posizione;

import java.util.Date;

public class Evento {
    private int id;
    private String nome;
    private String descrizione;
    private Date data;
    private String numeroPartecipanti;
    private Posizione posizione;

    public Evento(int id, String nome, String descrizione, Date data, Posizione posizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.posizione = posizione;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNuemroPartecipanti() {
        return nuemroPartecipanti;
    }

    public void setNuemroPartecipanti(String nuemroPartecipanti) {
        this.nuemroPartecipanti = nuemroPartecipanti;
    }

    public Posizione getPosizione() {
        return posizione;
    }

    public void setPosizione(Posizione posizione) {
        this.posizione = posizione;
    }
}
