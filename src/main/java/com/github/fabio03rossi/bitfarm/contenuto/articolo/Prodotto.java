package com.github.fabio03rossi.bitfarm.contenuto.articolo;

public class Prodotto extends AbstractArticolo {
    private int id = -1;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private String certs;

    public Prodotto(String nome, String descrizione, Double prezzo, String certs) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.certs = certs;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTipologia() {
        return "prodotto";
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

    public void setDescrizione(String description) {
        this.descrizione = description;
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
        return prezzo;
    }

    @Override
    public void setPrezzo(double prezzo) {

    }


    public void setPrice(Double price) {
        this.prezzo = price;
    }


}
