package com.github.fabio03rossi.bitfarm.account;

public class Utente extends Account {
    private String nickname;
    private String id;

    public Utente(String id, String nickname, String email, String password) {
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

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }
}
