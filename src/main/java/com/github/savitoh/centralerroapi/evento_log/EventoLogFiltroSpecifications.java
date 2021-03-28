package com.github.savitoh.centralerroapi.evento_log;

import com.github.savitoh.centralerroapi.common.BaseSpecification;
import com.github.savitoh.centralerroapi.evento_log.tipologlevel.TipoLogLevel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Component
public class EventoLogFiltroSpecifications extends BaseSpecification<EventoLog, EventoLogFiltro> {

    private Specification<EventoLog> filtrarLogLevel(final TipoLogLevel tipoLogLevel) {
        return specificationOrDefaultIfNull(
                tipoLogLevel,
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(EventoLog_.LEVEL), tipoLogLevel)
        );
    }

    private Specification<EventoLog> filtrarLog(final String log) {
        return specificationOrDefaultIfNull(
                log, (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(EventoLog_.LOG), log)
        );
    }

    private Specification<EventoLog> filtroDataGeracao(final LocalDateTime dataGeracao) {
        return specificationOrDefaultIfNull(
                dataGeracao,
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(EventoLog_.DATA_GERACAO), dataGeracao)
        );
    }

    private Specification<EventoLog> filtroQuantidade(final Integer quantidade) {
        return specificationOrDefaultIfNull(
                quantidade,
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(EventoLog_.QUANTIDADE), quantidade)
        );
    }

    public Specification<EventoLog> of(@NonNull final EventoLogFiltro filter) {
        Assert.notNull(filter, "[Assertion failed] - this argument is required; it must not be null");
        final Specification<EventoLog> specification = filtrarLogLevel(filter.getLevel());
        specification.and(filtrarLog(filter.getLog()));
        specification.and(filtroDataGeracao(filter.getDataGeracao()));
        specification.and(filtroQuantidade(filter.getQuantidade()));
        return specification;
    }

}
