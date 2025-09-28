package com.github.fabio03rossi.bitfarm.account;

import com.github.fabio03rossi.bitfarm.dto.UtenteDTO;

public class Utente extends Account {
    private String nome;
    private String indirizzo;

    public Utente(String nome, String email, String password, String indirizzo) {
        super(email, password);
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    public Utente(int id, String nome, String email, String password, String indirizzo) {
        super(email, password);
        this.nome = nome;
        this.indirizzo = indirizzo;
        setId(id);
    }


    public String getNome() {
        return this.nome;
    }

    public void setNickname(String nome) {
        this.nome = nome;
    }

    public int getId(){
        return super.getId();
    }

    public void setId(int id){
        super.setId(id);
    }

    public void setIndirizzo(String indirizzo){
        this.indirizzo = indirizzo;
    }

    public String getIndirizzo(){
        return this.indirizzo;
    }

    public UtenteDTO toDTO(){
        return new UtenteDTO(nome, getEmail(), getPassword(), indirizzo);
    }
}
