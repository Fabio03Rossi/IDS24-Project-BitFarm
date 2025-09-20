package com.github.fabio03rossi.bitfarm.contenuto.articolo;

public class Prodotto extends AbstractArticolo {
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

    @Override
    public String getTipologia() {
        return "prodotto";
    }

    public String getNome() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }

    public String getDescrizione() {
        return description;
    }

    public void setDescrizione(String description) {
        this.description = description;
    }

    @Override
    public String getCertificati() {
        return "";
    }

    @Override
    public void setCertificati(String certs) {

    }

    public int getIdSeller() {
        return 0;
    }

    public void setIdSeller(int idSeller) {

    }

    public double getPrezzo() {
        return price;
    }

    @Override
    public void setPrice(double price) {

    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
