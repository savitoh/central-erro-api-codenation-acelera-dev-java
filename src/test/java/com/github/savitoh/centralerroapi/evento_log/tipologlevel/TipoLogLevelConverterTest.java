package com.github.savitoh.centralerroapi.evento_log.tipologlevel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.stream.Stream;

class TipoLogLevelConverterTest {

    private TipoLogLevelConverter tipoLogLevelConverter;

    @BeforeEach
    void setUp() {
        tipoLogLevelConverter = new TipoLogLevelConverter();
    }

    @ParameterizedTest
    @MethodSource("proverTipoLogLevel")
    void test_convertToDatabaseColumn(TipoLogLevel tipoLogLevel, Short valorIdEsperado) {
        Assertions.assertEquals(valorIdEsperado, tipoLogLevelConverter.convertToDatabaseColumn(tipoLogLevel));
    }

    @ParameterizedTest
    @MethodSource("proverIdTipoLogLevel")
    void test_convertToEntityAttribute(Short idTipoLogLevel, TipoLogLevel tipoLogLevelEsperado) {
        Assertions.assertEquals(tipoLogLevelEsperado, tipoLogLevelConverter.convertToEntityAttribute(idTipoLogLevel));
    }


    @ParameterizedTest
    @ValueSource(shorts = {-2, 0, 4})
    @NullSource
    void test_convertToEntityAttribute_deveSubiException_quandoIdTipoLogLevelInexistente(Short idTipoLogLevelInexistente) {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> tipoLogLevelConverter.convertToEntityAttribute(idTipoLogLevelInexistente));
    }

    private static Stream<Arguments> proverTipoLogLevel() {
        return Arrays.stream(TipoLogLevel.values())
                .map(tipoLogLevel -> Arguments.of(tipoLogLevel, tipoLogLevel.getId()));
    }

    private static Stream<Arguments> proverIdTipoLogLevel() {
        return Arrays.stream(TipoLogLevel.values())
                .map(tipoLogLevel -> Arguments.of(tipoLogLevel.getId(), tipoLogLevel));
    }


}