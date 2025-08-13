package com.github.fabio03rossi.bitfarm.account;

public class Azienda extends Account {
    private String partitaIVA;
    private String nome;
    private String descrizione;
    private String indirizzo;
    private String telefono;
    private TipologiaAzienda tipologia;

    public String getPartitaIVA() {
        return partitaIVA;
    }

    public void setPartitaIVA(String partitaIVA) {
        this.partitaIVA = partitaIVA;
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

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public TipologiaAzienda getTipologia() {
        return tipologia;
    }

    public void setTipologia(TipologiaAzienda tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public String toString() {
        return this.getPartitaIVA().concat(" " + this.getNome()).concat(" " + this.getDescrizione());
    }
}
