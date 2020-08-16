package com.github.savitoh.centralerroapi.evento_log.tipologlevel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TipoLogLevelTest {

    @ParameterizedTest
    @ValueSource(shorts = {1, 2, 3})
    void quando_buscarPorIdExistente_retornaTipoLogLevel(Short idTipoLogLevelExistente) {
        Optional<TipoLogLevel> optionalTipoLogLevel = TipoLogLevel.getById(idTipoLogLevelExistente);

        Assertions.assertTrue(optionalTipoLogLevel.isPresent());
    }

    @ParameterizedTest
    @ValueSource(shorts = {-2, 0, 4})
    @NullSource
    void quando_buscarPorIdInexistente_retornaEmpty(Short idTipoLogLevelInexistente) {
        Optional<TipoLogLevel> optionalTipoLogLevel = TipoLogLevel.getById(idTipoLogLevelInexistente);

        Assertions.assertTrue(optionalTipoLogLevel.isEmpty());
    }
}