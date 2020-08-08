package com.github.savitoh.centralerroapi.validacao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

class TipoLogLevelExistsValidatorTest {

    private TipoLogLevelExistsValidator tipoLogLevelExistsValidator;

    @BeforeEach
    void setUp() {
        tipoLogLevelExistsValidator = new TipoLogLevelExistsValidator();
    }

    @ParameterizedTest
    @MethodSource("proverValoresExistentesTipoLogLevel")
    void retorna_true_quando_validar_codigos_tipologlevel_existentes(Short codigoTipoLogLevel, boolean resultadoEsperado) {
        Assertions.assertEquals(resultadoEsperado, tipoLogLevelExistsValidator.isValid(codigoTipoLogLevel, null));
    }

    @ParameterizedTest
    @MethodSource("proverValoresNaoExistentesTipoLogLevel")
    void retorna_false_quando_validar_codigos_tipologlevel_nao_existentes(Short codigoTipoLogLevel, boolean resultadoEsperado) {
        Assertions.assertEquals(resultadoEsperado, tipoLogLevelExistsValidator.isValid(codigoTipoLogLevel, null));
    }

    @ParameterizedTest
    @MethodSource("proverValoresInvalidosObjetosDiferenteShort")
    void retorna_false_quando_validar_codigos_tipologlevel_tipo_diferente_short
            (Object codigoTipoLogLevel, boolean resultadoEsperado) {
        Assertions.assertEquals(resultadoEsperado, tipoLogLevelExistsValidator.isValid(codigoTipoLogLevel, null));
    }

    @ParameterizedTest
    @NullSource
    void retorna_false_quando_validar_codigos_tipologlevel_nullo(Short codigoTipoLogLevel) {
        Assertions.assertFalse(tipoLogLevelExistsValidator.isValid(codigoTipoLogLevel, null));
    }

    private static Stream<Arguments> proverValoresExistentesTipoLogLevel() {
        return Stream.of(
                Arguments.of((short)1, true),
                Arguments.of((short)2, true),
                Arguments.of((short)3, true)
        );
    }

    private static Stream<Arguments> proverValoresNaoExistentesTipoLogLevel() {
        return Stream.of(
                Arguments.of((short)4, false),
                Arguments.of((short)5, false),
                Arguments.of((short)6, false)
        );
    }

    private static Stream<Arguments> proverValoresInvalidosObjetosDiferenteShort() {
        return Stream.of(
                Arguments.of("", false),
                Arguments.of(0L, false),
                Arguments.of(new Object(), false)
        );
    }

}