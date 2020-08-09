package com.github.savitoh.centralerroapi.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

class RecursoCriadoEventTest {

    private RecursoCriadoEvent<Long> recursoCriadoEvent;

    @BeforeEach
    void setUp() {
        final HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        final Long identificadorRecurso = 1L;
        recursoCriadoEvent = new RecursoCriadoEvent<>(this, httpServletResponse, identificadorRecurso);
    }

    @Test
    void testGetIdentificadorRecurso() {
        final HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        final Long identificadorRecurso = 1L;
        recursoCriadoEvent = new RecursoCriadoEvent<>(this, httpServletResponse, identificadorRecurso);

        Long indentificadorRecursoResult = this.recursoCriadoEvent.getIdentificadorRecurso();

        Assertions.assertNotNull(indentificadorRecursoResult);
        Assertions.assertEquals(identificadorRecurso, indentificadorRecursoResult);
    }

    @Test
    void testGetPathIdentificador() {
        final HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        final Long identificadorRecurso = 1L;
        this.recursoCriadoEvent = new RecursoCriadoEvent<>(this, httpServletResponse, identificadorRecurso);

        final String pathIdentificador = this.recursoCriadoEvent.getPathIdentificador();

        Assertions.assertNotNull(pathIdentificador);
        Assertions.assertEquals("/{id}", pathIdentificador);
    }

    @Test
    void test_getPathIdentificador_quando_inicializa_recursoCriadoEvent_com_nomeIdentificador() {
        final HttpServletResponse httpServletResponse = new MockHttpServletResponse();
        final Long identificadorRecurso = 1L;
        final String nomeIdentificador = "nomeIdentificador";
        this.recursoCriadoEvent =
                new RecursoCriadoEvent<>(this, httpServletResponse, nomeIdentificador, identificadorRecurso);

        final String pathIdentificador = this.recursoCriadoEvent.getPathIdentificador();

        Assertions.assertNotNull(pathIdentificador);
        Assertions.assertEquals("/{nomeIdentificador}", pathIdentificador);
    }

}