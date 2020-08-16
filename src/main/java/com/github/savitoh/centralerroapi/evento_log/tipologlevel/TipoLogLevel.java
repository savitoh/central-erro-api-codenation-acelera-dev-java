package com.github.savitoh.centralerroapi.evento_log.tipologlevel;

import java.util.Optional;
import java.util.stream.Stream;

public enum TipoLogLevel {

    ERROR((short)1, "error"),

    WARNING((short)2, "warning"),

    INFO((short)3, "info");

    private final Short id;

    private final String descricao;

    private TipoLogLevel(Short id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Short getId() {
        return id;
    }

    public static Optional<TipoLogLevel> getById(Short id) {
        return Stream.of(values())
            .filter(tipoLogLevel -> tipoLogLevel.getId().equals(id))
            .findFirst();
    }


    
}