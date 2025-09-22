package com.github.fabio03rossi.bitfarm.account;

public class Utente extends Account {
    private String nickname;
    private int id = -1;

    public Utente(String nickname, String email, String password) {
        super(email, password);
        this.nickname = nickname;
    }

    public Utente(int id, String nickname, String email, String password) {
        this(nickname, email, password);
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
}
