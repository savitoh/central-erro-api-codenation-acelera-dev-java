package com.github.savitoh.centralerroapi.seguranca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class AutenticacaoJwtEntryPointTest {

    private AutenticacaoJwtEntryPoint autenticacaoJwtEntryPoint;

    @BeforeEach
    void setUp() {
        autenticacaoJwtEntryPoint = new AutenticacaoJwtEntryPoint();
    }

    @Test
    void test_commence() throws IOException, ServletException {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException("Usuário não encontrado");

        autenticacaoJwtEntryPoint.commence(mockHttpServletRequest, mockHttpServletResponse, usernameNotFoundException);

        assertEquals(HttpStatus.UNAUTHORIZED.value(), mockHttpServletResponse.getStatus());
        assertTrue(mockHttpServletResponse.containsHeader(HttpHeaders.CONTENT_TYPE));
        assertNotNull(mockHttpServletResponse.getContentAsString());
    }
}