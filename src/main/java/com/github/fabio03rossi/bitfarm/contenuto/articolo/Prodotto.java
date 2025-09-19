package com.github.fabio03rossi.bitfarm.contenuto.articolo;

public class Prodotto implements IArticolo {
    private final int id;
    private String name;
    private String description;
    private Double price;
    private String certs;

    public Prodotto(int id, String nome, String descrizione, Double prezzo, String certs) {
        this.id = id;
        this.name = nome;
        this.description = descrizione;
        this.price = prezzo;
        this.certs = certs;
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

    @Override
    public String getCertificates() {
        return "";
    }

    @Override
    public void setCertificates(String certs) {

    }

    @Override
    public int getIdSeller() {
        return 0;
    }

    @Override
    public void setIdSeller(int idSeller) {

    }

    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {

    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
