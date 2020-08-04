package com.github.savitoh.centralerroapi.evento;

import com.github.savitoh.centralerroapi.evento.payload.NovoEventoRequestPayload;
import com.github.savitoh.centralerroapi.exception.RecuperaUsuarioException;
import com.github.savitoh.centralerroapi.seguranca.UserPrincipal;
import com.github.savitoh.centralerroapi.user.User;
import com.github.savitoh.centralerroapi.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/eventos")
public class EventoResource {

    private final EventoRepository eventoRepository;

    private final UserRepository userRepository;

    public EventoResource(EventoRepository eventoRepository, UserRepository userRepository) {
        this.eventoRepository = eventoRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> criarEvento(@Valid @RequestBody NovoEventoRequestPayload novoEventoRequestPayload,
                                              Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RecuperaUsuarioException("Não foi possivel recuperar o usuário da request (:"));
        Evento evento = novoEventoRequestPayload.toEvento(user);
        eventoRepository.save(evento);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(evento.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
