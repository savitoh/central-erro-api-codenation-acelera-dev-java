package com.github.savitoh.centralerroapi.usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UsuarioTest {

    private static final String NOME_USUARIO_TEST = "USARIO_TEST";

    private static final String LOGIN_USUARIO_TEST = "LOGIN_TEST";

    private static final String PASSWORD_TEXTO_PLANO = "SENHA_TEXTO_PLANO";

    private static final String PASSWORD_TEXTO_CRIPTOGRAFADO = "SENHA_CRIPTOGRAFADA";

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deve_Instanciar_Usuario() {
        final var usuarioTest = this.criarUsuarioTest();

        assertNotNull(usuarioTest);
        assertEquals(PASSWORD_TEXTO_CRIPTOGRAFADO, usuarioTest.getPassword());
        verify(passwordEncoder, times(1)).encode(PASSWORD_TEXTO_PLANO);
    }

    @Test
    void deve_converter_para_UsuarioResponsePayload() {
        final var usuarioTest = this.criarUsuarioTest();

        final var userResponsePayload = usuarioTest.toDetalheUserResponsePayload();

        assertNotNull(userResponsePayload);
        assertEquals(NOME_USUARIO_TEST, userResponsePayload.getNome());
        assertEquals(LOGIN_USUARIO_TEST, userResponsePayload.getLogin());
    }

    private Usuario criarUsuarioTest() {
        Mockito.when(passwordEncoder.encode(PASSWORD_TEXTO_PLANO)).thenReturn(PASSWORD_TEXTO_CRIPTOGRAFADO);
        return new Usuario(NOME_USUARIO_TEST, LOGIN_USUARIO_TEST, PASSWORD_TEXTO_PLANO, passwordEncoder);
    }

}