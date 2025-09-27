package com.github.fabio03rossi.bitfarm.account;

public class Curatore extends Account{
    private String nome;
    private String indirizzo;

    public Curatore(String email, String password, String nome, String indirizzo) {
        super(email, password);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public void setId(int id) {
        super.setId(id);
    }

    public int getId() {
        return super.getId();
    }
}
