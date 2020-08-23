package com.github.savitoh.centralerroapi.helpers;

import com.github.savitoh.centralerroapi.usuario.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsuarioTestMockBuilder {

    private static final String NOME_USUARIO_TEST = "USARIO_TEST";

    private static final String LOGIN_USUARIO_TEST = "LOGIN_TEST";

    private static final String PASSWORD_TEXTO_PLANO = "SENHA_TEXTO_PLANO";

    private static final String PASSWORD_TEXTO_CRIPTOGRAFADO = "SENHA_CRIPTOGRAFADA";


    private UsuarioTestMockBuilder() {

    }

    public static Usuario criar() {
        PasswordEncoder passwordEncoder = new CustomPasswordEncoder();
        return new Usuario(NOME_USUARIO_TEST, LOGIN_USUARIO_TEST, PASSWORD_TEXTO_PLANO, passwordEncoder);
    }

    static class CustomPasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence charSequence) {
            return PASSWORD_TEXTO_CRIPTOGRAFADO;
        }

        @Override
        public boolean matches(CharSequence charSequence, String s) {
            return false;
        }
    }
}
