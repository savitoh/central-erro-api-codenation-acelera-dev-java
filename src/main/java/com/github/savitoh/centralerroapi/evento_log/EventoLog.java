package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.evento_log.payload.EventoLogDetalheResponsePayload;
import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;
import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevelConverter;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import com.github.savitoh.centralerroapi.usuario.payload.UsuarioResponsePayload;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class EventoLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENTO_LOG_SEQ")
    @SequenceGenerator(name="EVENTO_LOG_SEQ", sequenceName = "EVENTO_LOG_SEQ", allocationSize=1)
    @Column(name = "evento_log_id")
    private Long id;

    @NotNull
    @Convert(converter = TipoLogLevelConverter.class)
    @Column(name = "level_id", nullable = false)
    private TipoLogLevel level;

    @NotEmpty
    @Size(max = 1000)
    @Column(length = 1000, nullable = false)
    private String descricao;

    @Lob
    @NotEmpty
    @Column
    private String log;

    @Past
    @NotNull
    @Column(columnDefinition = "timestamp", nullable = false)
    private LocalDateTime dataGeracao;

    @NotNull
    @Column(nullable = false)
    private Integer quantidade;
    
    @Column(columnDefinition = "timestamp")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * @deprecated(Usado apenas pelo Hibernate)
     */
    @Deprecated(since = "27/07/2020", forRemoval = false)
    public EventoLog() {
    }

    public EventoLog(@NotNull TipoLogLevel level, @NotEmpty @Max(1000) String descricao,
                     @NotEmpty String log, @Past @NotNull LocalDateTime dataGeracao,
                     @NotNull Integer quantidade, @NotNull Usuario usuario) {
        this.level = level;
        this.descricao = descricao;
        this.log = log;
        this.dataGeracao = dataGeracao;
        this.quantidade = quantidade;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public EventoLogDetalheResponsePayload toEventoLogResponsePayload() {
        final UsuarioResponsePayload usuarioResponsePayload = usuario.toDetalheUserResponsePayload();
        return new EventoLogDetalheResponsePayload(this.level, this.descricao, this.log, this.dataGeracao, this.quantidade, usuarioResponsePayload);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoLog eventoLog = (EventoLog) o;
        return Objects.equals(id, eventoLog.id) &&
                level == eventoLog.level &&
                Objects.equals(descricao, eventoLog.descricao) &&
                dataGeracao.equals(eventoLog.dataGeracao) &&
                quantidade.equals(eventoLog.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, descricao, dataGeracao, quantidade);
    }
}