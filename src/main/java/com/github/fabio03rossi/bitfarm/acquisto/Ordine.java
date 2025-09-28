package com.github.fabio03rossi.bitfarm.acquisto;

public class Ordine {
    private int id;
    private String indirizzo;
    private String metodoPagamento;
    private Carrello carrello;
    private int idUtente;

    public Ordine(String indirizzo, int idUtente, Carrello carrello, String metodoPagamento) {
        this.indirizzo = indirizzo;
        this.idUtente = idUtente;
        this.carrello = carrello;
        this.indirizzo = indirizzo;
        this.metodoPagamento = metodoPagamento;
    }

    public Ordine(String indirizzo, int idUtente, String metodoPagamento) {
        this.indirizzo = indirizzo;
        this.idUtente = idUtente;
        this.indirizzo = indirizzo;
        this.metodoPagamento = metodoPagamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public double getTotale() {
        return this.carrello.getTotale();
    }

    public int getIdUtente() {
        return idUtente;
    }

    public Carrello getCarrello() {
        return carrello;
    }

    public void setCarrello(Carrello carrello) {
        this.carrello = carrello;
    }

    public String getMetodoDiPagamento() {
        return metodoPagamento;
    }
}
