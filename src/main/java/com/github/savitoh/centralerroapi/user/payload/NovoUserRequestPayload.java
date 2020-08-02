package com.github.savitoh.centralerroapi.user.payload;

import com.github.savitoh.centralerroapi.validacao.UniqueKey;
import com.github.savitoh.centralerroapi.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NovoUserRequestPayload {

    @NotBlank
    @Size(max = 50)
    private final String nome;

    @NotBlank
    @Size(min = 4, max = 12)
    @UniqueKey(className = User.class, columnName = "login")
    private final String login;

    @NotBlank
    @Size(min = 4, max = 8)
    private final String password;

    public NovoUserRequestPayload(@NotBlank @Size(max = 50) String nome,
                                  @NotBlank @Size(min = 4, max = 12) String login,
                                  @NotBlank @Size(min = 4, max = 8) String password) {
        this.nome = nome;
        this.login = login;
        this.password = password;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(this.nome, this.login, this.password, passwordEncoder);
    }

    


    
    
}