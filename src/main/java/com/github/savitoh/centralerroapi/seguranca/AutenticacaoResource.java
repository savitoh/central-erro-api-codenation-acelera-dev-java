package com.github.savitoh.centralerroapi.seguranca;

import com.github.savitoh.centralerroapi.seguranca.jwt.TokenManager;
import com.github.savitoh.centralerroapi.seguranca.payload.LoginRequestPayload;
import com.github.savitoh.centralerroapi.seguranca.payload.TokenResponsePayload;
import com.github.savitoh.centralerroapi.seguranca.propriedades.TipoToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AutenticacaoResource {


    private final AuthenticationManager authManager;

    private final TokenManager tokenManager;

    public AutenticacaoResource(AuthenticationManager authManager, TokenManager tokenManager) {
        this.authManager = authManager;
        this.tokenManager = tokenManager;
    }

    @PostMapping
    public ResponseEntity<TokenResponsePayload> autenticar(@Valid @RequestBody LoginRequestPayload loginRequestPayload) {

        UsernamePasswordAuthenticationToken authenticationToken = loginRequestPayload.toUsernamePasswordAuthenticationToken();
        Authentication authentication = authManager.authenticate(authenticationToken);
        final String jwt = tokenManager.gerarToken(authentication);
        TokenResponsePayload tokenResponse = new TokenResponsePayload(TipoToken.BEARER.getDescricao(), jwt);
        return ResponseEntity.ok(tokenResponse);
    }
}
