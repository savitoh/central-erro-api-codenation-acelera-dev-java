package com.github.savitoh.centralerroapi.user;

public class UserResponsePayload {

    public final String nome;

    public final String login;

    public UserResponsePayload(String nome, String login) {
        this.nome = nome;
        this.login = login;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    
    
}