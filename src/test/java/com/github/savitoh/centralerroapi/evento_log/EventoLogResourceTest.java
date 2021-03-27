package com.github.savitoh.centralerroapi.evento_log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savitoh.centralerroapi.evento_log.payload.NovoEventoLogRequestPayload;
import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;
import com.github.savitoh.centralerroapi.helpers.UsuarioTestMockBuilder;
import com.github.savitoh.centralerroapi.seguranca.UserPrincipal;
import com.github.savitoh.centralerroapi.seguranca.jwt.TokenManager;
import com.github.savitoh.centralerroapi.seguranca.service.UserPrincipalDetailsService;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EventoLogResource.class)
class EventoLogResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EventoLogRepository eventoLogRepository;

    @MockBean
    private EventoLogFiltroSpecifications eventoLogSpecification;

    @MockBean
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @MockBean
    private TokenManager tokenManager;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private ApplicationEventPublisher publisher;

    @Test
    void should_return_EventoLog_when_exists_byId() throws Exception {
        final long eventoLogId = 1;
        final EventoLog eventoLog = new EventoLog(
                TipoLogLevel.INFO,
                "Log Info",
                "adfgh",
                LocalDateTime.now(),
                1,
                UsuarioTestMockBuilder.criar()
        );
        when(eventoLogRepository.findById(eventoLogId)).thenReturn(Optional.of(eventoLog));
        final UserPrincipal userPrincipal = new UserPrincipal(UsuarioTestMockBuilder.criar());

        this.mockMvc
                .perform(
                        get("/api/v1/eventos/{id}", eventoLogId)
                        .with(user(userPrincipal))
                )
                .andExpect(status().isOk());
    }


    @Test
    void should_return_notFoud_when_notExists_byId() throws Exception {
        final long eventoLogId = 1;
        when(eventoLogRepository.findById(eventoLogId)).thenReturn(Optional.empty());
        final UserPrincipal userPrincipal = new UserPrincipal(UsuarioTestMockBuilder.criar());

        this.mockMvc
                .perform(
                        get("/api/v1/eventos/{id}", eventoLogId)
                                .with(user(userPrincipal))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_unauthorized_when_getEventByIdAndRequestNotAuthenticated() throws Exception {
        final long eventoLogId = 1;
        final EventoLog eventoLog = new EventoLog(
                TipoLogLevel.INFO,
                "Log Info",
                "adfgh",
                LocalDateTime.now(),
                1,
                UsuarioTestMockBuilder.criar()
        );
        when(eventoLogRepository.findById(eventoLogId)).thenReturn(Optional.of(eventoLog));

        this.mockMvc
                .perform(
                        get("/api/v1/eventos/{id}", eventoLogId)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_return_ListEventsLog_when_filtered() throws Exception {
        when(eventoLogRepository.findAll(ArgumentMatchers.<Specification<EventoLog>>any(), any(Pageable.class)))
                .thenReturn(Page.empty());
        final UserPrincipal userPrincipal = new UserPrincipal(UsuarioTestMockBuilder.criar());

        this.mockMvc
                .perform(
                        get("/api/v1/eventos")
                                .with(user(userPrincipal))
                )
                .andExpect(status().isOk());
    }

    @Test
    void should_return_unauthorized_when_filteredAndRequestNotAuthenticated() throws Exception {
        this.mockMvc
                .perform(
                        get("/api/v1/eventos/")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void should_create_event() throws Exception {
        final NovoEventoLogRequestPayload requestPayload = new NovoEventoLogRequestPayload(
                TipoLogLevel.INFO.getId(),
                "Log Info",
                "adfgh",
                LocalDateTime.now(),
                1
        );
        final UserPrincipal userPrincipal = new UserPrincipal(UsuarioTestMockBuilder.criar());
        final Usuario usuario = UsuarioTestMockBuilder.criar();
        when(usuarioRepository.findById(userPrincipal.getId())).thenReturn(Optional.of(usuario));
        this.mockMvc
                .perform(
                        post("/api/v1/eventos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPayload))
                        .with(user(userPrincipal))
                        .with(csrf())
                )
                .andExpect(status().isCreated());
    }

    @Test
    void should_return_unauthorizedStatus_when_createEventAndRequestNotAuthenticated() throws Exception {
        final NovoEventoLogRequestPayload requestPayload = new NovoEventoLogRequestPayload(
                TipoLogLevel.INFO.getId(),
                "Log Info",
                "adfgh",
                LocalDateTime.now(),
                1
        );
        this.mockMvc
                .perform(
                        post("/api/v1/eventos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestPayload))
                                .with(csrf())
                )
                .andExpect(status().isUnauthorized());
    }
}