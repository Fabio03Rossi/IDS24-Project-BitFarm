package com.github.fabio03rossi.bitfarm.account;

public class Utente extends Account {
    private String nickname;
    private int id;

    public Utente(int id, String nickname, String email, String password) {
        this.id = id;
        this.nickname = nickname;
        this.setEmail(email);
        this.setPassword(password);
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
