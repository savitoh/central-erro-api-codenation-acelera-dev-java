package com.github.savitoh.centralerroapi.seguranca.jwt;

import com.github.savitoh.centralerroapi.seguranca.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenManager {


    private final String secret;

    private final long expirationInMillis;

    public TokenManager(@Value("${jwt.secret}") String secret,
                        @Value("${jwt.expiration}") long expirationInMillis) {
        this.secret = secret;
        this.expirationInMillis = expirationInMillis;
    }

    public String gerarToken(Authentication authentication) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + this.expirationInMillis);

        return Jwts.builder()
                .setIssuer("Codenation Central Erro API")
                .setSubject(Long.toString(principal.getId()))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }

    public boolean ehValido(String jwt) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(jwt);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Integer getUserId(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(this.secret)
                .parseClaimsJws(jwt).getBody();

        return Integer.parseInt(claims.getSubject());
    }



}
