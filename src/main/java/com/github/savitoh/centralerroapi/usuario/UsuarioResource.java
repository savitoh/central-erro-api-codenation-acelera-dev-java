package com.github.savitoh.centralerroapi.usuario;

import com.github.savitoh.centralerroapi.event.RecursoCriadoEvent;
import com.github.savitoh.centralerroapi.exception.RecursoNaoEncontradoException;
import com.github.savitoh.centralerroapi.usuario.payload.NovoUsuarioRequestPayload;
import com.github.savitoh.centralerroapi.usuario.payload.UsuarioResponsePayload;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
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

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponsePayload> getUserById(@PathVariable("id") Integer id) {
        return usuarioRepository.findById(id)
            .map(user -> ResponseEntity.ok().body(user.toDetalheUserResponsePayload()))
            .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Usuário com identificador: %s não encontrado", id)));
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody @Valid NovoUsuarioRequestPayload novoUsuarioRequestPayload,
                                               HttpServletResponse response) {
        Usuario usuario = novoUsuarioRequestPayload.toUser(passwordEncoder);
        usuarioRepository.save(usuario);
        publisher.publishEvent(new RecursoCriadoEvent<>(this, response, usuario.getId()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    
}