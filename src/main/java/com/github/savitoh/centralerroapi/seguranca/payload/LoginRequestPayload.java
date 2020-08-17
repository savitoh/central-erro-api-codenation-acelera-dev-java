package com.github.savitoh.centralerroapi.seguranca.payload;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

public class LoginRequestPayload {

    @NotEmpty
    private final String password;

    @NotEmpty
    private final String login;

    public LoginRequestPayload(@NotEmpty String password, @NotEmpty String login) {
        this.password = password;
        this.login = login;
    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(this.login, this.password);
    }

}
