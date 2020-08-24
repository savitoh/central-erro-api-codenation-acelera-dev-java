package com.github.savitoh.centralerroapi.usuario.payload;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioResponsePayload that = (UsuarioResponsePayload) o;
        return Objects.equals(nome, that.nome) &&
                Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, login);
    }
}