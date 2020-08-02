package com.github.savitoh.centralerroapi.user.payload;

public class UserResponsePayload {

    private final String nome;

    private final String login;

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