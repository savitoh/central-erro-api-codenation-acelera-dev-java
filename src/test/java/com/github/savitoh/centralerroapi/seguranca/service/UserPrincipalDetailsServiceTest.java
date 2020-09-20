package com.github.savitoh.centralerroapi.seguranca.service;

import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.usuario.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserPrincipalDetailsServiceTest {

    private static final String USER_LOGIN_MOCK = "USER_LOGIN_MOCK";

    private static final Integer USER_ID_MOCK = 1;

    @Mock
    private UsuarioRepository usuarioRepository;

    private UserPrincipalDetailsService userPrincipalDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userPrincipalDetailsService = new UserPrincipalDetailsService(usuarioRepository);
    }


    @Test
    void quandoEncontrarUsuarioPorLoginEntaoRetornaUserDetails() {
        Mockito.when(usuarioRepository.findByLogin(USER_LOGIN_MOCK)).thenReturn(Optional.of(new Usuario()));

        UserDetails userDetails = userPrincipalDetailsService.loadUserByUsername(USER_LOGIN_MOCK);

        assertNotNull(userDetails);
    }

    @Test
    void quandoNaoEncontrarUsuarioPorLoginEntaoSobeExceptionUsernameNotFoundException() {
        Mockito.when(usuarioRepository.findByLogin(USER_LOGIN_MOCK)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                ()-> userPrincipalDetailsService.loadUserByUsername(USER_LOGIN_MOCK),
                "Não foi possivel encontrar usuário com login: " + USER_LOGIN_MOCK);
    }

    @Test
    void quandoEncontrarUsuarioPorIdEntaoRetornaUserDetails() {
        Mockito.when(usuarioRepository.findById(USER_ID_MOCK)).thenReturn(Optional.of(new Usuario()));

        UserDetails userDetails = userPrincipalDetailsService.loadUserById(USER_ID_MOCK);

        assertNotNull(userDetails);
    }

    @Test
    void quandoNaoEncontrarUsuarioPorIdEntaoSobeExceptionUsernameNotFoundException() {
        Mockito.when(usuarioRepository.findById(USER_ID_MOCK)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                ()-> userPrincipalDetailsService.loadUserById(USER_ID_MOCK),
                "Não foi possível encontrar o usuário com id: " + USER_LOGIN_MOCK);
    }

}