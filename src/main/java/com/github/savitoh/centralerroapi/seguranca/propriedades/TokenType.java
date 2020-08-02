package com.github.savitoh.centralerroapi.seguranca.propriedades;

public enum TokenType {

    BEARER("Bearer");

    private final String descricao;

    TokenType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
