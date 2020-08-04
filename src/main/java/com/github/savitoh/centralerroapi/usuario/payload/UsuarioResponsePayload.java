package com.github.savitoh.centralerroapi.usuario.payload;

public class UsuarioResponsePayload {

    private final String nome;

    private final String login;

    public UsuarioResponsePayload(String nome, String login) {
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