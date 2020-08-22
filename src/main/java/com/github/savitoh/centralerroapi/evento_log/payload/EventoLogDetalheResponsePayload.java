package com.github.savitoh.centralerroapi.evento_log.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;
import com.github.savitoh.centralerroapi.usuario.payload.UsuarioResponsePayload;

import java.time.LocalDateTime;

public class EventoLogDetalheResponsePayload {

    private final TipoLogLevel level;

    private final String descricao;

    private final String log;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "Etc/GMT+3")
    private final LocalDateTime dataGeracao;

    private final Integer quantidade;

    private final UsuarioResponsePayload usuarioResponsePayload;

    public EventoLogDetalheResponsePayload(TipoLogLevel level, String descricao, String log, LocalDateTime dataGeracao,
                                           Integer quantidade, UsuarioResponsePayload usuarioResponsePayload) {
        this.level = level;
        this.descricao = descricao;
        this.log = log;
        this.dataGeracao = dataGeracao;
        this.quantidade = quantidade;
        this.usuarioResponsePayload = usuarioResponsePayload;
    }


    public TipoLogLevel getLevel() {
        return level;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLog() {
        return log;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public UsuarioResponsePayload getUsuarioResponsePayload() {
        return usuarioResponsePayload;
    }
}
