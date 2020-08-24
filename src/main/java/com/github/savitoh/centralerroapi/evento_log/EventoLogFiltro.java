package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;

import java.time.LocalDateTime;
import java.util.Objects;

final class EventoLogFiltro {

    private final TipoLogLevel level;

    private final String descricao;

    private final String log;

    private final LocalDateTime dataGeracao;

    private final Integer quantidade;

    public EventoLogFiltro(Short level, String descricao, String log, LocalDateTime dataGeracao, Integer quantidade) {
        this.level = Objects.nonNull(level) ? TipoLogLevel.getById(level).orElseThrow(IllegalArgumentException::new) : null;
        this.descricao = descricao;
        this.log = log;
        this.dataGeracao = dataGeracao;
        this.quantidade = quantidade;
    }

    public TipoLogLevel getLevel() {
        return level;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getLog() {
        return log;
    }
}
