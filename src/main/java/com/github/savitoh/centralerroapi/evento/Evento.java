package com.github.savitoh.centralerroapi.evento;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.savitoh.centralerroapi.evento.tipologlevel.TipoLogLevel;
import com.github.savitoh.centralerroapi.evento.tipologlevel.TipoLogLevelConverter;
import com.github.savitoh.centralerroapi.usuario.Usuario;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENTO_SEQUENCE")
    @SequenceGenerator(name="EVENTO_SEQUENCE", sequenceName = "EVENTO_SEQ", allocationSize=1)
    @Column(name = "evento_id")
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

    @NotNull
    @Column(columnDefinition = "timestamp", nullable = false)
    private LocalDateTime dataGeracao;

    @NotNull
    @Column
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
    public Evento() {
    }

    public Evento(@NotNull TipoLogLevel level, @NotEmpty @Max(1000) String descricao,
                  @NotEmpty String log, @NotNull LocalDateTime dataGeracao,
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((dataGeracao == null) ? 0 : dataGeracao.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Evento other = (Evento) obj;
        if (createdAt == null) {
            if (other.createdAt != null)
                return false;
        } else if (!createdAt.equals(other.createdAt))
            return false;
        if (dataGeracao == null) {
            if (other.dataGeracao != null)
                return false;
        } else if (!dataGeracao.equals(other.dataGeracao))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (level != other.level)
            return false;
        if (quantidade == null) {
            if (other.quantidade != null)
                return false;
        } else if (!quantidade.equals(other.quantidade))
            return false;
        return true;
    }

    
    
}