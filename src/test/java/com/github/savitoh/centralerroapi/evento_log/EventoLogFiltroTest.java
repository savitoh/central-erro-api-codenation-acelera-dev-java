package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventoLogFiltroTest {

    private static final String DESCRICAO_EVENTO_LOG = "Evento Log Level de Test";

    private static final String LOG_EVENTO_LOG = "LOG Evento Log";

    private static final LocalDateTime DATA_GERACAO_EVENTO_LOG = LocalDateTime.now();

    private static final Integer QUANTIDADE_EVENTO_LOG = 2;

    @Test
    void deve_instanciar_FiltroEventoLog() {
        final Short levelErrorId = TipoLogLevel.ERROR.getId();

        var filtroEventoLog  =
                new EventoLogFiltro(levelErrorId, DESCRICAO_EVENTO_LOG,  LOG_EVENTO_LOG, DATA_GERACAO_EVENTO_LOG, QUANTIDADE_EVENTO_LOG);

        assertNotNull(filtroEventoLog);
        assertEquals(LOG_EVENTO_LOG, filtroEventoLog.getLog());
        assertEquals(DESCRICAO_EVENTO_LOG, filtroEventoLog.getDescricao());
        assertEquals(QUANTIDADE_EVENTO_LOG, filtroEventoLog.getQuantidade());
        assertEquals(TipoLogLevel.ERROR, filtroEventoLog.getLevel());
        assertNotNull(filtroEventoLog.getDataGeracao());
    }

    @Test
    void deve_instanciar_FiltroEventoLog_passando_level_nullo() {
        var filtroEventoLog  =
                new EventoLogFiltro(null, DESCRICAO_EVENTO_LOG, LOG_EVENTO_LOG, DATA_GERACAO_EVENTO_LOG, QUANTIDADE_EVENTO_LOG);

        assertNotNull(filtroEventoLog);
        assertEquals(LOG_EVENTO_LOG, filtroEventoLog.getLog());
        assertEquals(DESCRICAO_EVENTO_LOG, filtroEventoLog.getDescricao());
        assertEquals(QUANTIDADE_EVENTO_LOG, filtroEventoLog.getQuantidade());
        assertNotNull(filtroEventoLog.getDataGeracao());
        assertNull(filtroEventoLog.getLevel());
    }

    @Test
    void deve_subir_exception_quando_instanciar_FiltroEventoLog_passando_LogLevelError_inexistente() {
        final Short failLevelErrorId = (short)0;

        assertThrows(IllegalArgumentException.class,
                ()-> new EventoLogFiltro(failLevelErrorId, DESCRICAO_EVENTO_LOG, LOG_EVENTO_LOG, DATA_GERACAO_EVENTO_LOG, QUANTIDADE_EVENTO_LOG));
    }

}