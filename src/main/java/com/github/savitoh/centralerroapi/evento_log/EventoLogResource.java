package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.event.RecursoCriadoEvent;
import com.github.savitoh.centralerroapi.evento_log.payload.EventoLogResponsePayload;
import com.github.savitoh.centralerroapi.evento_log.payload.NovoEventoLogRequestPayload;
import com.github.savitoh.centralerroapi.exception.RecuperaUsuarioException;
import com.github.savitoh.centralerroapi.seguranca.UserPrincipal;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.usuario.UsuarioRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoLogResource {

    private final EventoLogRepository eventoLogRepository;

    private final UsuarioRepository usuarioRepository;

    private final ApplicationEventPublisher publisher;

    public EventoLogResource(EventoLogRepository eventoLogRepository,
                             UsuarioRepository usuarioRepository,
                             ApplicationEventPublisher publisher) {
        this.eventoLogRepository = eventoLogRepository;
        this.usuarioRepository = usuarioRepository;
        this.publisher = publisher;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoLogResponsePayload> getById(@PathVariable("id") Long id) {
        return eventoLogRepository.findById(id)
                .map(evento -> ResponseEntity.ok(evento.toEventoLogResponsePayload()))
                .orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping
    public ResponseEntity<String> criarEvento(@Valid @RequestBody NovoEventoLogRequestPayload novoEventoLogRequestPayload,
                                              Authentication authentication,
                                              HttpServletResponse response) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RecuperaUsuarioException("Não foi possivel recuperar o usuário da request (:"));
        EventoLog eventoLog = novoEventoLogRequestPayload.toEvento(usuario);
        eventoLogRepository.save(eventoLog);
        publisher.publishEvent(new RecursoCriadoEvent<>(this, response, eventoLog.getId()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
