package com.github.savitoh.centralerroapi.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class URIUtilTest {

    @Test
    void deveCriarPath() {
        final String nomePath = "mora";

        final String path = URIUtil.criarPathTemplate(nomePath);

        Assertions.assertEquals("/{mora}", path);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void deveSubirIllegalArgumentExceptionQuandoCriarPathComValoresInvalidos(String nomePath) {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            URIUtil.criarPathTemplate(nomePath);
        });
    }

    @ParameterizedTest
    @NullSource
    void deveSubirIllegalArgumentExceptionQuandoCriarPathComValorNullo(String nomePath) {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            URIUtil.criarPathTemplate(nomePath);
        });
    }


}