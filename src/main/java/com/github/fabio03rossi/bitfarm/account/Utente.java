package com.github.fabio03rossi.bitfarm.account;

public class Utente extends Account {
    private String nickname;
    private int id = -1;
    private String indirizzo;

    public Utente(String nickname, String email, String password, String indirizzo) {
        super(email, password);
        this.nickname = nickname;
        this.indirizzo = indirizzo;
    }

    public Utente(int id, String nickname, String email, String password, String indirizzo) {
        this(nickname, email, password, indirizzo);
        this.id = id;
    }


    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
