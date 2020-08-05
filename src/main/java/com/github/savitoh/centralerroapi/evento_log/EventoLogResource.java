package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.evento_log.payload.EventoLogResponsePayload;
import com.github.savitoh.centralerroapi.evento_log.payload.NovoEventoLogRequestPayload;
import com.github.savitoh.centralerroapi.exception.RecuperaUsuarioException;
import com.github.savitoh.centralerroapi.seguranca.UserPrincipal;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.usuario.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoLogResource {

    private final EventoLogRepository eventoLogRepository;

    private final UsuarioRepository usuarioRepository;

    public EventoLogResource(EventoLogRepository eventoLogRepository, UsuarioRepository usuarioRepository) {
        this.eventoLogRepository = eventoLogRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoLogResponsePayload> getById(@PathVariable("id") Long id) {
        return eventoLogRepository.findById(id)
                .map(evento -> ResponseEntity.ok(evento.toEventoLogResponsePayload()))
                .orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping
    public ResponseEntity<String> criarEvento(@Valid @RequestBody NovoEventoLogRequestPayload novoEventoLogRequestPayload,
                                              Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RecuperaUsuarioException("Não foi possivel recuperar o usuário da request (:"));
        EventoLog eventoLog = novoEventoLogRequestPayload.toEvento(usuario);
        eventoLogRepository.save(eventoLog);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(eventoLog.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
