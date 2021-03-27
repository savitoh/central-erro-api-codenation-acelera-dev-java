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
        return conjuctionPredicateIfNull(tipoLogLevel)
                .orElse((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(EventoLog_.LEVEL), tipoLogLevel));
    }

    private Specification<EventoLog> filtrarLog(final String log) {
        return conjuctionPredicateIfNull(log)
                .orElse((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(EventoLog_.LOG), log));
    }

    private Specification<EventoLog> filtroDataGeracao(final LocalDateTime dataGeracao) {
        return conjuctionPredicateIfNull(dataGeracao)
                .orElse((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(EventoLog_.DATA_GERACAO), dataGeracao));
    }

    public Specification<EventoLog> of(@NonNull final EventoLogFiltro filter) {
        Assert.notNull(filter, "[Assertion failed] - this argument is required; it must not be null");
        final Specification<EventoLog> specification = filtrarLogLevel(filter.getLevel());
        specification.and(filtrarLog(filter.getLog()));
        specification.and(filtroDataGeracao(filter.getDataGeracao()));
        return specification;
    }

}
