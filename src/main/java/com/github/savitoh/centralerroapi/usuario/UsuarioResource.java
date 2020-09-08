package com.github.savitoh.centralerroapi.usuario;

import com.github.savitoh.centralerroapi.event.RecursoCriadoEvent;
import com.github.savitoh.centralerroapi.exception.RecursoNaoEncontradoException;
import com.github.savitoh.centralerroapi.exception.payload.ApiErrorResponsePayload;
import com.github.savitoh.centralerroapi.usuario.payload.NovoUsuarioRequestPayload;
import com.github.savitoh.centralerroapi.usuario.payload.UsuarioResponsePayload;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Usuário API", description = "API de criação e recuperação Usuários.")
@SecurityRequirement(name = "auth-scheme")
public class UsuarioResource {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher publisher;


    public UsuarioResource(UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher publisher) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.publisher = publisher;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Recupera um usuário pelo identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário recuperado.",
                    content = @Content(schema = @Schema(implementation = UsuarioResponsePayload.class))),
            @ApiResponse(responseCode = "401", description = "Não foi possivél autenticar a requisição.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
    })
    public ResponseEntity<UsuarioResponsePayload> getUserById(
            @Parameter(description="Identificador do usuário a ser recuperado", required=true) @PathVariable("id") Integer id) {
        return usuarioRepository.findById(id)
            .map(user -> ResponseEntity.ok().body(user.toDetalheUserResponsePayload()))
            .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Usuário com identificador: %s não encontrado", id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um novo usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário Criado."),
            @ApiResponse(responseCode = "400", description = "Payload recebido não contempla as regras de validação.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
            @ApiResponse(responseCode = "401", description = "Não foi possivél autenticar a requisição.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class)))
    })
    public ResponseEntity<Void> criarUsuario(
            @Parameter(description="Payload Novo usuário") @RequestBody @Valid NovoUsuarioRequestPayload novoUsuarioRequestPayload,
            HttpServletResponse response) {
        Usuario usuario = novoUsuarioRequestPayload.toUser(passwordEncoder);
        usuarioRepository.save(usuario);
        publisher.publishEvent(new RecursoCriadoEvent<>(this, response, usuario.getId()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    
}