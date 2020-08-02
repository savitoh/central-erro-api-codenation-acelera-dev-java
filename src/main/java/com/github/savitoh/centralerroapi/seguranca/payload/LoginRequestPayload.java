package com.github.savitoh.centralerroapi.seguranca.payload;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class LoginRequestPayload {

    private final String password;

    private final String login;

    public LoginRequestPayload(String password, String login) {
        this.password = password;
        this.login = login;
    }

    public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(this.login, this.password);
    }

}
