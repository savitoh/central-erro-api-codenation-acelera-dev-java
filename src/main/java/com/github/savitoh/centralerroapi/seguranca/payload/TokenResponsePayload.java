package com.github.savitoh.centralerroapi.seguranca.payload;

public class TokenResponsePayload {

    private final String tokenType;
    private final String token;

    public TokenResponsePayload(String tokenType, String token) {
        this.tokenType = tokenType;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

}
