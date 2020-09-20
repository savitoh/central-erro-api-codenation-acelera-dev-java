package com.github.savitoh.centralerroapi.seguranca.enums;

public enum TipoToken {

    BEARER("Bearer");

    private final String descricao;

    TipoToken(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
