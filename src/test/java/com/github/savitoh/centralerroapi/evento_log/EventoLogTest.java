package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;
import com.github.savitoh.centralerroapi.helpers.UsuarioTestMockBuilder;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventoLogTest {


    private static final TipoLogLevel TIPO_LOG_LEVEL_EVENTOLOG_TEST = TipoLogLevel.ERROR;

    private static final String DESCRICAO_EVENTOLOG_TEST = "EventoLog descricao test";

    private static final String LOG_EVENTOLOG_TEST = "EventoLog log test";

    private static final LocalDateTime DATA_GERACAO_EVENTOLOG_TEST = LocalDateTime.now();

    private static final Integer QUANTIDADE_EVENTOLOG_TEST = 2;

    private static final Usuario USUARIO_EVENTOLOG_TEST = UsuarioTestMockBuilder.criar();

    @Test
    void deve_converter_para_EventoLogDetalheResponsePayload() {
        EventoLog eventoLogTest = new EventoLog(
                TIPO_LOG_LEVEL_EVENTOLOG_TEST,
                DESCRICAO_EVENTOLOG_TEST,
                LOG_EVENTOLOG_TEST,
                DATA_GERACAO_EVENTOLOG_TEST,
                QUANTIDADE_EVENTOLOG_TEST,
                USUARIO_EVENTOLOG_TEST
        );

        var eventoLogDetalheResponsePayload = eventoLogTest.toEventoLogDetalheResponsePayload();
        var usuarioResponsePayload = eventoLogDetalheResponsePayload.getUsuarioResponsePayload();

        assertNotNull(eventoLogDetalheResponsePayload);
        assertEquals(TIPO_LOG_LEVEL_EVENTOLOG_TEST, eventoLogDetalheResponsePayload.getLevel());
        assertEquals(DESCRICAO_EVENTOLOG_TEST, eventoLogDetalheResponsePayload.getDescricao());
        assertEquals(LOG_EVENTOLOG_TEST, eventoLogDetalheResponsePayload.getLog());
        assertEquals(DATA_GERACAO_EVENTOLOG_TEST, eventoLogDetalheResponsePayload.getDataGeracao());
        assertEquals(QUANTIDADE_EVENTOLOG_TEST, eventoLogDetalheResponsePayload.getQuantidade());
        assertEquals(USUARIO_EVENTOLOG_TEST.toDetalheUserResponsePayload(), usuarioResponsePayload);
    }

    @Test
    void deve_converter_para_EventoLogResponsePayload() {
        EventoLog eventoLogTest = new EventoLog(
                TIPO_LOG_LEVEL_EVENTOLOG_TEST,
                DESCRICAO_EVENTOLOG_TEST,
                LOG_EVENTOLOG_TEST,
                DATA_GERACAO_EVENTOLOG_TEST,
                QUANTIDADE_EVENTOLOG_TEST,
                USUARIO_EVENTOLOG_TEST
        );

        var eventoLogResponsePayload = eventoLogTest.toEventoLogResponsePayload();

        assertNotNull(eventoLogResponsePayload);
        assertEquals(TIPO_LOG_LEVEL_EVENTOLOG_TEST, eventoLogResponsePayload.getLevel());
        assertEquals(DESCRICAO_EVENTOLOG_TEST, eventoLogResponsePayload.getDescricao());
        assertEquals(DATA_GERACAO_EVENTOLOG_TEST, eventoLogResponsePayload.getDataGeracao());
        assertEquals(QUANTIDADE_EVENTOLOG_TEST, eventoLogResponsePayload.getQuantidade());
    }
}