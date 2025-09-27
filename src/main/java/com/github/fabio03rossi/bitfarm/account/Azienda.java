package com.github.fabio03rossi.bitfarm.account;

public class Azienda extends Account {
    private String partitaIVA;
    private String nome;
    private String descrizione;
    private String indirizzo;
    private String telefono;
    private String tipologia;
    private String certificazioni;

    public Azienda(String partitaIVA, String nome, String email, String password, String descrizione, String indirizzo, String telefono, String tipologia, String certificazioni) {
        super(email, password);
        this.partitaIVA = partitaIVA;
        this.nome = nome;
        this.descrizione = descrizione;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.tipologia = tipologia;
    }

    public int getId() {
        return super.getId();
    }

    public void setId(int id)
    {
        super.setId(id);
    }

    public String getCertificazioni() {
        return certificazioni;
    }

    public void setCertificazioni(String certificazioni) {
        this.certificazioni = certificazioni;
    }

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

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    @Override
    public String toString() {
        return this.getPartitaIVA().concat(" " + this.getNome()).concat(" " + this.getDescrizione());
    }
}
