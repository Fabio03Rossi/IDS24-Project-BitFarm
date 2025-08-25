package com.github.fabio03rossi.bitfarm.post;

import java.util.List;

public class Prodotto implements IArticolo {
    private final int id;
    private String name;
    private String description;
    private Double price;
    private String certifications;

public Prodotto(int id, String nome, String descrizione, Double prezzo, String certificazioni) {
    this.id = id;
    this.name = nome;
    this.description = descrizione;
    this.price = prezzo;
    this.certifications = certificazioni;
}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }
}
