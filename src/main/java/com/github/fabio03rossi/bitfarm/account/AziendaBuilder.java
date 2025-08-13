package com.github.fabio03rossi.bitfarm.account;

public class AziendaBuilder implements AccountBuilder<Azienda> {
    Azienda azienda = new Azienda();

    @Override
    public void reset() {
        this.azienda = new Azienda();
    }

    @Override
    public AziendaBuilder email(String email) {
        this.azienda.setEmail(email);
        return this;
    }

    @Override
    public AziendaBuilder password(String password) {
        this.azienda.setPassword(password);
        return this;
    }

    public AziendaBuilder partitaIVA(String partitaIVA) {
        this.azienda.setPartitaIVA(partitaIVA);
        return this;
    }

    public AziendaBuilder nome(String nome) {
        this.azienda.setNome(nome);
        return this;
    }

    public AziendaBuilder descrizione(String descrizione) {
        this.azienda.setDescrizione(descrizione);
        return this;
    }

    public AziendaBuilder indirizzo(String indirizzo) {
        this.azienda.setIndirizzo(indirizzo);
        return this;
    }

    public AziendaBuilder telefono(String telefono) {
        this.azienda.setTelefono(telefono);
        return this;
    }

    public AziendaBuilder tipologia(TipologiaAzienda tipologia) {
        this.azienda.setTipologia(tipologia);
        return this;
    }

    public Azienda getResult() {
        return this.azienda;
    }
}
