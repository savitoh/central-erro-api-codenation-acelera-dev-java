package com.github.savitoh.centralerroapi.evento_log.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;

import java.time.LocalDateTime;

public class EventoLogResponsePayload {

    private final Long identificador;

    private final TipoLogLevel level;

    private final String descricao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "Etc/GMT+3")
    private final LocalDateTime dataGeracao;

    private final Integer quantidade;

    public EventoLogResponsePayload(Long identificador, TipoLogLevel level, String descricao, LocalDateTime dataGeracao, Integer quantidade) {
        this.identificador = identificador;
        this.level = level;
        this.descricao = descricao;
        this.dataGeracao = dataGeracao;
        this.quantidade = quantidade;
    }

    public Long getIdentificador() {
        return identificador;
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
}
