package com.github.savitoh.centralerroapi.common.validation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import javax.validation.Payload;
import java.lang.annotation.Annotation;
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

    @Test
    void retorna_true_quando_codigo_nullo_for_permitido() {
        this.tipoLogLevelExistsValidator.initialize(new TipoLogLevelExistisFaker());

        Assertions.assertTrue(tipoLogLevelExistsValidator.isValid(null, null));
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

    private static class TipoLogLevelExistisFaker implements TipoLogLevelExists {

        @Override
        public String message() {
            return "";
        }

        @Override
        public boolean permitNull() {
            return true;
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }
    }

}