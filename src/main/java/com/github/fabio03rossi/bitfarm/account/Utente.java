package com.github.fabio03rossi.bitfarm.account;

public class Utente extends Account {
    private String nome;
    private int id = -1;
    private String indirizzo;

    public Utente(String nome, String email, String password, String indirizzo) {
        super(email, password);
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    public Utente(int id, String nome, String email, String password, String indirizzo) {
        this(nome, email, password, indirizzo);
        this.id = id;
    }


    public String getNome() {
        return this.nome;
    }

    public void setNickname(String nome) {
        this.nome = nome;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo(){
        return this.indirizzo;
    }
}
