package com.github.savitoh.centralerroapi.evento;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENTO_SEQUENCE")
    @SequenceGenerator(name="EVENTO_SEQUENCE", sequenceName = "EVENTO_SEQ", allocationSize=1)
    @Column(name = "evento_id")
    private Long id;

    @Convert(converter = TipoLogLevelConverter.class)
    private TipoLogLevel level;

    @NotEmpty
    @Max(1000)
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

    /**
     * @deprecated(Usado apenas pelo Hibernate)
     */
    @Deprecated(since = "27/07/2020", forRemoval = false)
    public Evento() {
    }

    public Evento(TipoLogLevel level, @NotEmpty @Max(1000) String descricao, @NotEmpty String log,
            @NotNull LocalDateTime dataGeracao, @NotNull Integer quantidade) {
        this.level = level;
        this.descricao = descricao;
        this.log = log;
        this.dataGeracao = dataGeracao;
        this.quantidade = quantidade;
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