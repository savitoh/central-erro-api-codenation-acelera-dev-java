package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.common.BaseSpecification;
import com.github.savitoh.centralerroapi.event.RecursoCriadoEvent;
import com.github.savitoh.centralerroapi.evento_log.payload.EventoLogDetalheResponsePayload;
import com.github.savitoh.centralerroapi.evento_log.payload.EventoLogResponsePayload;
import com.github.savitoh.centralerroapi.evento_log.payload.NovoEventoLogRequestPayload;
import com.github.savitoh.centralerroapi.exception.RecuperaUsuarioException;
import com.github.savitoh.centralerroapi.exception.RecursoNaoEncontradoException;
import com.github.savitoh.centralerroapi.exception.payload.ApiErrorResponsePayload;
import com.github.savitoh.centralerroapi.seguranca.UserPrincipal;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.usuario.UsuarioRepository;
import com.github.savitoh.centralerroapi.common.validation.TipoLogLevelExists;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Validated
@RestController
@RequestMapping("/api/v1/eventos")
@Tag(name = "Evento Log API", description = "API de criação e recuperação de Eventos LOG.")
@SecurityRequirement(name = "auth-scheme")
public class EventoLogResource {

    private final EventoLogRepository eventoLogRepository;

    private final UsuarioRepository usuarioRepository;

    private final ApplicationEventPublisher publisher;

    private final BaseSpecification<EventoLog, EventoLogFiltro> eventoLogFiltroSpecifications;

    public EventoLogResource(EventoLogRepository eventoLogRepository,
                             UsuarioRepository usuarioRepository,
                             ApplicationEventPublisher publisher,
                             EventoLogFiltroSpecifications eventoLogFiltroSpecifications) {
        this.eventoLogRepository = eventoLogRepository;
        this.usuarioRepository = usuarioRepository;
        this.publisher = publisher;
        this.eventoLogFiltroSpecifications = eventoLogFiltroSpecifications;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Recupera um Evento LOG pelo identificador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evento LOG recuperado.",
                    content = @Content(schema = @Schema(implementation = EventoLogDetalheResponsePayload.class))),
            @ApiResponse(responseCode = "401", description = "Não foi possivél autenticar a requisição.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
            @ApiResponse(responseCode = "404", description = "Evento LOG não encontrado.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
    })
    public ResponseEntity<EventoLogDetalheResponsePayload> recuperarPorIdentificador(
            @Parameter(description="Identificador do Evento LOG a ser recuperado", required=true)@PathVariable("id") Long id) {
        return eventoLogRepository.findById(id)
                .map(evento -> ResponseEntity.ok(evento.toEventoLogDetalheResponsePayload()))
                .orElseThrow(() -> new RecursoNaoEncontradoException(String.format("Evento LOG com identificador: %s não encontrado", id)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista  Eventos LOG por filtros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eventos LOG filtrados.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EventoLogResponsePayload.class)))),
            @ApiResponse(responseCode = "400", description = "Parametro(s) recebido(s) não contempla(m) as regras de validação.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
            @ApiResponse(responseCode = "401", description = "Não foi possivél autenticar a requisição.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
    })

    @Parameter(name = "page", description = "número da página",
            in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0"))
    @Parameter(name = "size", description = "tamanho da página",
            in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    @Parameter(name = "sort", description = "coluna a ser ordenada",
            in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = ""))
    @Parameter(name = "direction", description = "direção a ser ordenada",
            in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "ASC", allowableValues = {"ASC", "DESC"}))
    public Page<EventoLogResponsePayload> recuperarPorFiltro(
            @Parameter(description="Código do Tipo Log Level", in=ParameterIn.QUERY, schema=@Schema(type="integer", allowableValues = {"1", "2", "3"}))
            @TipoLogLevelExists(permitNull = true) @RequestParam(required = false)
                    Short level,
            @Parameter(description="Param Descricao", in=ParameterIn.QUERY, schema=@Schema(type="string"))
            @RequestParam(required = false)
                    String descricao,
            @Parameter(description="Param log", in=ParameterIn.QUERY, schema=@Schema(type="string"))
            @RequestParam(required=false)
                    String log,
            @Parameter(description="Param Data Geração", in=ParameterIn.QUERY, schema=@Schema(type="string", pattern="dd/MM/yyyy HH:mm:ss"))
            @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") @RequestParam(value = "data_geracao", required = false)
                    LocalDateTime dataGeracao,
            @Parameter(description="Param Quantidade", in=ParameterIn.QUERY, schema=@Schema(type="integer"))
            @RequestParam(required = false) @PositiveOrZero
                    Integer quantidade,
            @Parameter(hidden = true) @PageableDefault Pageable pageable) {

        EventoLogFiltro eventoLogFiltro = new EventoLogFiltro(level, log, descricao, dataGeracao, quantidade);
        Specification<EventoLog> specifications = eventoLogFiltroSpecifications.of(eventoLogFiltro);

        return eventoLogRepository.findAll(specifications, pageable).map(EventoLog::toEventoLogResponsePayload);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um novo Evento LOG.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento LOG Criado."),
            @ApiResponse(responseCode = "400", description = "Payload recebido não contempla as regras de validação.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class))),
            @ApiResponse(responseCode = "401", description = "Não foi possivél autenticar a requisição.",
                    content = @Content(schema = @Schema(implementation = ApiErrorResponsePayload.class)))
    })
    public ResponseEntity<Void> criarEvento(
            @Parameter(description="Payload Novo Evento LOG") @Valid @RequestBody
                    NovoEventoLogRequestPayload novoEventoLogRequestPayload,
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
