package com.github.fabio03rossi.bitfarm.post;

public class Prodotto {
    private int id;
    private String nome;
    private String descrizione;
    private Double prezzo;
    private String certificazioni [];

public Prodotto(int id, String nome, String descrizione, Double prezzo, String certificazioni[]) {
    this.id = id;
    this.nome = nome;
    this.descrizione = descrizione;
    this.prezzo = prezzo;
    this.certificazioni = certificazioni;

}
}
