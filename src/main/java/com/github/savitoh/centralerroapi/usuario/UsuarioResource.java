package com.github.savitoh.centralerroapi.usuario;

import com.github.savitoh.centralerroapi.usuario.payload.NovoUsuarioRequestPayload;
import com.github.savitoh.centralerroapi.usuario.payload.UsuarioResponsePayload;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UsuarioResource {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public UsuarioResource(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponsePayload> getUserById(@PathVariable("id") Integer id) {
        return usuarioRepository.findById(id)
            .map(user -> ResponseEntity.ok().body(user.toDetalheUserResponsePayload()))
            .orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody @Valid NovoUsuarioRequestPayload novoUsuarioRequestPayload) {
        Usuario usuario = novoUsuarioRequestPayload.toUser(passwordEncoder);
        usuarioRepository.save(usuario);
        URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(usuario.getId())
                    .toUri();
        return ResponseEntity.created(location).build();
    }


    
}