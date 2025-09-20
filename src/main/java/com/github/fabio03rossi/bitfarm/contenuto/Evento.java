package com.github.fabio03rossi.bitfarm.contenuto;

import java.util.Date;

public class Evento extends Contenuto {
    private int id;
    private String nome;
    private String descrizione;
    private Date data;
    private int numeroPartecipanti;
    private String indirizzo;

    public Evento(int id, String nome, String descrizione, Date data, String posizione) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.data = data;
        this.indirizzo = posizione;
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

    public int getNumeroPartecipanti() {
        return numeroPartecipanti;
    }

    public void setNumeroPartecipanti(int nuemroPartecipanti) {
        this.numeroPartecipanti = nuemroPartecipanti;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public IStatoContenuto getStato() {
        return null;
    }

    @Override
    public void setStato(IStatoContenuto stato) {

    }
}
