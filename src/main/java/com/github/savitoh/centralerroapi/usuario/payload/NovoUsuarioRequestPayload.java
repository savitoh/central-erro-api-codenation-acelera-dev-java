package com.github.savitoh.centralerroapi.usuario.payload;

import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.validacao.UniqueKey;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NovoUsuarioRequestPayload {

    @NotBlank
    @Size(max = 50)
    private final String nome;

    @NotBlank
    @Size(min = 4, max = 12)
    @UniqueKey(className = Usuario.class, columnName = "login")
    private final String login;

    @NotBlank
    @Size(min = 4, max = 8)
    private final String password;

    public NovoUsuarioRequestPayload(@NotBlank @Size(max = 50) String nome,
                                     @NotBlank @Size(min = 4, max = 12) String login,
                                     @NotBlank @Size(min = 4, max = 8) String password) {
        this.nome = nome;
        this.login = login;
        this.password = password;
    }

    public Usuario toUser(PasswordEncoder passwordEncoder) {
        return new Usuario(this.nome, this.login, this.password, passwordEncoder);
    }

    


    
    
}