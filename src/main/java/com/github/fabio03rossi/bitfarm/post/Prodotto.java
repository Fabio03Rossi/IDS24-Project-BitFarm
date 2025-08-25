package com.github.fabio03rossi.bitfarm.post;

import java.util.ArrayList;
import java.util.List;

public class Prodotto implements IArticolo {
    private final int id;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private List<String> certificazioni;

public Prodotto(int id, String nome, String descrizione, Double prezzo, List<String> certificazioni) {
    this.id = id;
    this.nome = nome;
    this.descrizione = descrizione;
    this.prezzo = prezzo;
    this.certificazioni = certificazioni;
}

    public int getId() {
        return id;
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

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    public List<String> getCertificazioni() {
        return certificazioni;
    }

    public void setCertificazioni(List<String> certificazioni) {
        this.certificazioni = certificazioni;
    }
}
