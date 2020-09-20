package com.github.savitoh.centralerroapi.seguranca.payload;

import io.swagger.v3.oas.annotations.media.Schema;
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

    /**
     * @deprecated (usado apenas pela  lib Open API docs)
     *
     */
    @Deprecated(since = "08/09/2020")
    @Schema(example = "minha_senha", description = "Senha do usuário")
    public String getPassword() {
        return password;
    }

    /**
     * @deprecated (usado apenas pela  lib Open API docs)
     * @return
     */
    @Deprecated(since = "08/09/2020")
    @Schema(example = "meu_login", description = "Login do usuário")
    public String getLogin() {
        return login;
    }
}
