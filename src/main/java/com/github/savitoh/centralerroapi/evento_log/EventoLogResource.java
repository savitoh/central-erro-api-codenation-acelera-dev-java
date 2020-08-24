package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.event.RecursoCriadoEvent;
import com.github.savitoh.centralerroapi.evento_log.payload.EventoLogDetalheResponsePayload;
import com.github.savitoh.centralerroapi.evento_log.payload.EventoLogResponsePayload;
import com.github.savitoh.centralerroapi.evento_log.payload.NovoEventoLogRequestPayload;
import com.github.savitoh.centralerroapi.exception.RecuperaUsuarioException;
import com.github.savitoh.centralerroapi.exception.RecursoNaoEncontradoException;
import com.github.savitoh.centralerroapi.seguranca.UserPrincipal;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.usuario.UsuarioRepository;
import com.github.savitoh.centralerroapi.validacao.TipoLogLevelExists;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Validated
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
    public ResponseEntity<EventoLogDetalheResponsePayload> recuperarPorIdentificador(@PathVariable("id") Long id) {
        return eventoLogRepository.findById(id)
                .map(evento -> ResponseEntity.ok(evento.toEventoLogDetalheResponsePayload()))
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Evento LOG com identificador: %s não encontrado", id)));
    }

    @GetMapping
    public Page<EventoLogResponsePayload> recuperarPorFiltro(
            @TipoLogLevelExists @RequestParam(required = false) Short level,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String log,
            @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") @RequestParam(value = "data_geracao", required = false) LocalDateTime dataGeracao,
            @RequestParam(required = false) Integer quantidade,
            @PageableDefault Pageable pageable) {

        EventoLogFiltro eventoLogFiltro = new EventoLogFiltro(level, log, descricao, dataGeracao, quantidade);
        Specification<EventoLog> specifications = EventoLogFiltroSpecifications.of(eventoLogFiltro);

        return eventoLogRepository.findAll(specifications, pageable).map(EventoLog::toEventoLogResponsePayload);
    }

    @PostMapping
    public ResponseEntity<String> criarEvento(
            @Valid @RequestBody NovoEventoLogRequestPayload novoEventoLogRequestPayload,
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
