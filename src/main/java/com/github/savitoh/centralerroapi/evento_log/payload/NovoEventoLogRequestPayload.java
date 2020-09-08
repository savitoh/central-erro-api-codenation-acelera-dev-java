package com.github.savitoh.centralerroapi.evento_log.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.savitoh.centralerroapi.evento_log.EventoLog;
import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.validacao.TipoLogLevelExists;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class NovoEventoLogRequestPayload {

    @NotNull
    @TipoLogLevelExists
    private Short levelCodigo;

    @NotBlank
    @Size(max = 1000)
    private String descricao;

    @NotBlank
    private String log;

    @Past
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", locale = "pt-BR", timezone = "Etc/GMT+3")
    private LocalDateTime dataGeracao;

    @NotNull
    private Integer quantidade;

    /**
     * @deprecated(Usado apenas pelo Jackson)
     */
    @Deprecated(forRemoval = false)
    public NovoEventoLogRequestPayload() {
    }

    public NovoEventoLogRequestPayload(@NotNull Short levelCodigo, @NotEmpty @Max(1000) String descricao,
                                       @NotEmpty String log, @Past @NotNull LocalDateTime dataGeracao,
                                       @NotNull Integer quantidade) {
        this.levelCodigo = levelCodigo;
        this.descricao = descricao;
        this.log = log;
        this.dataGeracao = dataGeracao;
        this.quantidade = quantidade;
    }

    public void setLevelCodigo(Short levelCodigo) {
        this.levelCodigo = levelCodigo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }


    /**
     * @deprecated (usado apenas pela  lib  Open API docs)
     *
     */
    @Schema(description = "Código do Tipo Level LOG", type = "integer", allowableValues = {"1", "2", "3"})
    public Short getLevelCodigo() {
        return levelCodigo;
    }

    /**
     * @deprecated (usado apenas pela  lib  Open API docs)
     *
     */
    @Schema(example = "descricao evento", description = "Descrição do evento LOG")
    public String getDescricao() {
        return descricao;
    }

    /**
     * @deprecated (usado apenas pela  lib  Open API docs)
     *
     */
    @Schema(description = "stack trace do erro")
    public String getLog() {
        return log;
    }

    /**
     * @deprecated (usado apenas pela  lib  Open API docs)
     *
     */
    @Schema(example = "04/12/2020 22:10:56", description = "Data que foi gerado o LOG",
            pattern = "dd/MM/yyyy HH:mm:ss", type = "string")
    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    /**
     * @deprecated (usado apenas pela  lib  Open API docs)
     *
     */
    @Schema(example = "1", description = "Quantidade de ocorrências do evento")
    public Integer getQuantidade() {
        return quantidade;
    }

    public EventoLog toEvento(Usuario usuario) {
        TipoLogLevel tipoLogLevel = TipoLogLevel.getById(levelCodigo).orElseThrow(IllegalArgumentException::new);
        return new EventoLog(tipoLogLevel, descricao, log, dataGeracao, quantidade, usuario);
    }
}
