package com.github.savitoh.centralerroapi.seguranca;

import com.github.savitoh.centralerroapi.exception.payload.ApiErrorResponsePayload;
import com.github.savitoh.centralerroapi.seguranca.jwt.TokenManager;
import com.github.savitoh.centralerroapi.seguranca.payload.LoginRequestPayload;
import com.github.savitoh.centralerroapi.seguranca.payload.TokenResponsePayload;
import com.github.savitoh.centralerroapi.seguranca.propriedades.TipoToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticação API", description = "API para autenticação de usuários.")
public class AutenticacaoResource {


    private final AuthenticationManager authManager;

    private final TokenManager tokenManager;

    public AutenticacaoResource(AuthenticationManager authManager, TokenManager tokenManager) {
        this.authManager = authManager;
        this.tokenManager = tokenManager;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Cria token JWT para autenticação na aplicação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Token Criado com sucesso.",
                    content = @Content(schema = @Schema(implementation = TokenResponsePayload.class))),
            @ApiResponse(responseCode = "400", description = "Payload recebido não contempla as regras de validação.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
            @ApiResponse(responseCode = "401", description = "Não foi possivél autenticar a requisição.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class)))
    })
    public TokenResponsePayload autenticar(
            @Parameter(description = "Payload Login Autenticação") @Valid @RequestBody LoginRequestPayload loginRequestPayload) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestPayload.toUsernamePasswordAuthenticationToken();
        Authentication authentication = authManager.authenticate(authenticationToken);
        final String jwt = tokenManager.gerarToken(authentication);
        return new TokenResponsePayload(TipoToken.BEARER.getDescricao(), jwt);
    }
}
